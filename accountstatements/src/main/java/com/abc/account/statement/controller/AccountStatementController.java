package com.abc.account.statement.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abc.account.exception.InvalidInputException;
import com.abc.account.statement.constant.AccountStatementConstants;
import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.UserRequest;
import com.abc.account.statement.service.AccountStatementService;
import com.abc.account.statement.util.AccountStatementUtil;

/**
 * @author Subatra Shankar Controller class to handle user requests related to
 *         the accounts.
 */
@RestController
@ComponentScan(basePackages = { "com.abc.account.exception" })
public class AccountStatementController {

	@Autowired
	AccountStatementService accountStatementService;

	final private Logger logger = LoggerFactory.getLogger(AccountStatementController.class);

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(path = "/securedadmin/account/statement/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<AccountStatement> getAccountStatement(@PathVariable Integer id,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = AccountStatementConstants.DD_MM_YYYY) String fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = AccountStatementConstants.DD_MM_YYYY) String toDate,
			@RequestParam(name = "fromAmount", required = false) Double fromAmount,
			@RequestParam(name = "toAmount", required = false) Double toAmount) throws InvalidInputException {
		if (null == id || 0 == id) {
			throw new InvalidInputException("Account Id is not valid");
		}
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			AccountStatementUtil.validateUserRequestedDates(fromDate, toDate);
		}
		if (null != fromAmount && null != toAmount) {
			AccountStatementUtil.validateUserRequestedAmountValues(fromAmount, toAmount);
		}
		logger.info("Inside StatementController class. Trying to fetch the account statement details for account id {}",
				id);
		final UserRequest userRequest = new UserRequest();
		userRequest.setId(id);
		userRequest.setFromDate(fromDate);
		userRequest.setToDate(toDate);
		userRequest.setFromAmount(fromAmount);
		userRequest.setToAmount(toAmount);
		final AccountStatement accountStatement = accountStatementService.getAccountStatement(userRequest);
		accountStatement.setMessage("Retrieved account statement details successfully");
		logger.info("Account statement retrieved successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(accountStatement);
	}

	@PreAuthorize("hasAnyRole('USER')")
	@GetMapping(path = "/secureduser/account/statement/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<AccountStatement> getAccountStatement(@PathVariable Integer id)
			throws InvalidInputException {
		if (null == id || 0 == id) {
			throw new InvalidInputException("Account Id is not valid");
		}
		logger.info("Inside StatementController class. Trying to fetch the account statement details for account id {}",
				id);
		final UserRequest userRequest = new UserRequest();
		userRequest.setId(id);
		final AccountStatement accountStatement = accountStatementService.getAccountStatement(userRequest);
		accountStatement.setMessage("Retrieved account statement details successfully");
		logger.info("Account statement retrieved successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(accountStatement);
	}
}