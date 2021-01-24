package com.abc.account.statement.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.account.statement.entity.Account;
import com.abc.account.statement.entity.Statement;
import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.UserRequest;
import com.abc.account.statement.repository.AccountRepository;
import com.abc.account.statement.service.impl.AccountStatementServiceImpl;

/**
 * @author Subatra Shankar
 *
 */
@RunWith(SpringRunner.class)
public class AccountStatementServiceTest {
	private static final String ACCOUNT_TYPE = "CURRENT";
	private static final String ACCOUNT_NUMBER = "123412341234";
	private static final String MASKED_ACCOUNT_NUMBER = "XXXXXXXX1234";

	@InjectMocks
	private AccountStatementServiceImpl statementService;

	@Mock
	private AccountRepository accountRepository;

	@Test
	public void getAccountStatement() {
		final UserRequest userRequest = mockUserRequest(1, null, null, null, null);
		final List<Integer> ids = new ArrayList<>();
		ids.add(userRequest.getId());
		when(accountRepository.findAllById(ids)).thenReturn(mockAccountData());
		final AccountStatement accountStatement = statementService.getAccountStatement(userRequest);
		assertNotNull(accountStatement);
		assertTrue(accountStatement.getAccountId() == 1);
		assertTrue(MASKED_ACCOUNT_NUMBER.equals(accountStatement.getAccountNumber()));
		assertTrue(ACCOUNT_TYPE.equals(accountStatement.getAccountType()));
		assertNotNull(accountStatement.getTransactionDetails());
		assertTrue(accountStatement.getTransactionDetails().size() == 1);
	}

	@Test
	public void getAccountStatementForDateRange() {
		final UserRequest userRequest = mockUserRequest(1, null, null, "12.01.2020", "12.03.2020");
		final List<Integer> ids = new ArrayList<>();
		ids.add(userRequest.getId());
		when(accountRepository.findAllById(ids)).thenReturn(mockAccountData());
		final AccountStatement accountStatement = statementService.getAccountStatement(userRequest);
		assertNotNull(accountStatement);
		assertTrue(accountStatement.getAccountId() == 1);
		assertTrue(MASKED_ACCOUNT_NUMBER.equals(accountStatement.getAccountNumber()));
		assertTrue(ACCOUNT_TYPE.equals(accountStatement.getAccountType()));
		assertNotNull(accountStatement.getTransactionDetails());
		assertTrue(accountStatement.getTransactionDetails().size() == 2);
	}

	@Test
	public void getAccountStatementForAmountRange() {
		final UserRequest userRequest = mockUserRequest(1, 100.0, 200.0, null, null);
		final List<Integer> ids = new ArrayList<>();
		ids.add(userRequest.getId());
		when(accountRepository.findAllById(ids)).thenReturn(mockAccountData());
		final AccountStatement accountStatement = statementService.getAccountStatement(userRequest);
		assertNotNull(accountStatement);
		assertTrue(accountStatement.getAccountId() == 1);
		assertTrue(MASKED_ACCOUNT_NUMBER.equals(accountStatement.getAccountNumber()));
		assertTrue(ACCOUNT_TYPE.equals(accountStatement.getAccountType()));
		assertNotNull(accountStatement.getTransactionDetails());
		assertTrue(accountStatement.getTransactionDetails().size() == 4);
	}

	private UserRequest mockUserRequest(final Integer id, final Double fromAmount, final Double toAmount,
			final String fromDate, final String toDate) {
		final UserRequest userRequest = new UserRequest();
		userRequest.setId(1);
		userRequest.setFromAmount(fromAmount);
		userRequest.setToAmount(toAmount);
		userRequest.setFromDate(fromDate);
		userRequest.setToDate(toDate);
		return userRequest;
	}

	private List<Account> mockAccountData() {
		final List<Account> accountData = new ArrayList<>();
		final Account account = new Account();
		account.setAccountNumber(ACCOUNT_NUMBER);
		account.setId(1);
		account.setAccountType(ACCOUNT_TYPE);
		final List<Statement> transactions = new ArrayList<>();
		final Statement tr1 = new Statement("15.01.2020", "150.803506518358");
		final Statement tr2 = new Statement("10.02.2020", "900.803506518358");
		final Statement tr3 = new Statement("20.03.2020", "80.803506518358");
		final Statement tr4 = new Statement("08.05.2020", "100.803506518358");
		final Statement tr5 = new Statement("18.07.2020", "160.803506518358");
		final Statement tr6 = new Statement("20.12.2020", "160.803506518358");
		transactions.add(tr1);
		transactions.add(tr2);
		transactions.add(tr3);
		transactions.add(tr4);
		transactions.add(tr5);
		transactions.add(tr6);
		account.setTransactions(transactions);
		accountData.add(account);
		return accountData;
	}
}
