/**
 * 
 */
package com.abc.account.statement.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.account.statement.constant.AccountStatementConstants;
import com.abc.account.statement.entity.Account;
import com.abc.account.statement.entity.Statement;
import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.AccountTransaction;
import com.abc.account.statement.model.UserRequest;
import com.abc.account.statement.repository.AccountRepository;
import com.abc.account.statement.service.AccountStatementService;
import com.abc.account.statement.util.AccountStatementUtil;

/**
 * @author Subatra Shankar
 *
 */
@Service
public class AccountStatementServiceImpl implements AccountStatementService {

	@Autowired
	AccountRepository accountRepository;

	final private Logger logger = LoggerFactory.getLogger(AccountStatementServiceImpl.class);

	public AccountStatement getAccountStatement(final UserRequest userRequest) {
		final List<Integer> ids = new ArrayList<>();
		ids.add(userRequest.getId());
		logger.info("In StatmentServiceImpl. Trying to fetch account statement details for account with account id: {}",
				userRequest.getId());
		final List<Account> accounts = accountRepository.findAllById(ids);
		final AccountStatement accountStatement = new AccountStatement();
		if (StringUtils.isNotEmpty(userRequest.getFromDate()) && StringUtils.isNotEmpty(userRequest.getToDate())) {
			logger.info("Filtering statement details based on user requested date values");
			accounts.stream().forEach(account -> {
				final Stream<Statement> filteredTransactions = account.getTransactions().stream()
						.sorted(AccountStatementUtil.dateComparator).filter(dateFilter(userRequest));
				populateAccountStatement(accountStatement, account, filteredTransactions);
			});
		} else if (null != userRequest.getFromAmount() && null != userRequest.getToAmount()) {
			logger.info("Filtering statement details based on user requested amount values");
			accounts.stream().forEach(account -> {
				final Stream<Statement> filteredTransactions = account.getTransactions().stream()
						.sorted(Comparator.comparing(Statement::getAmount).reversed())
						.filter(amountFilter(userRequest));
				populateAccountStatement(accountStatement, account, filteredTransactions);
			});
		} else {
			logger.info("Populating statement details for the last three months");
			userRequest.setFromDate(
					LocalDate.now().minusMonths(AccountStatementConstants.DISPLAY_TRANSACTIONS_DEFAULT_DURATION_MONTHS)
							.format(DateTimeFormatter.ofPattern(AccountStatementConstants.DD_MM_YYYY, Locale.ENGLISH))
							.toString());
			userRequest.setToDate(LocalDate.now()
					.format(DateTimeFormatter.ofPattern(AccountStatementConstants.DD_MM_YYYY, Locale.ENGLISH))
					.toString());
			accounts.stream().forEach(account -> {
				final Stream<Statement> filteredTransactions = account.getTransactions().stream()
						.sorted(AccountStatementUtil.dateComparator).filter(dateFilter(userRequest));
				populateAccountStatement(accountStatement, account, filteredTransactions);
			});
		}
		return accountStatement;
	}

	private Predicate<? super Statement> amountFilter(final UserRequest userRequest) {
		return transaction -> (Double.valueOf(transaction.getAmount()).compareTo(userRequest.getFromAmount()) >= 0
				&& Double.valueOf(transaction.getAmount()).compareTo(userRequest.getToAmount()) <= 0);
	}

	private void populateAccountStatement(final AccountStatement filteredAccountStatement, Account account,
			final Stream<Statement> filteredTransactions) {
		final List<AccountTransaction> transactionDetails = new ArrayList<>();
		filteredTransactions.forEach(filteredTransaction -> transactionDetails
				.add(new AccountTransaction(filteredTransaction.getDatefield(), filteredTransaction.getAmount())));
		filteredAccountStatement.setAccountId(account.getId());
		filteredAccountStatement.setAccountNumber(AccountStatementUtil.hashAccountNumber(account.getAccountNumber()));
		filteredAccountStatement.setAccountType(account.getAccountType());
		filteredAccountStatement.setTransactionDetails(transactionDetails);
	}

	private Predicate<? super Statement> dateFilter(final UserRequest userRequest) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AccountStatementConstants.DD_MM_YYYY, Locale.ENGLISH);
		return transaction -> !LocalDate.parse(transaction.getDatefield(), formatter)
				.isBefore(LocalDate.parse(userRequest.getFromDate(), formatter))
				&& !LocalDate.parse(transaction.getDatefield(), formatter)
						.isAfter(LocalDate.parse(userRequest.getToDate(), formatter));
	}
}
