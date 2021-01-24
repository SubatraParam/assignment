/**
 * 
 */
package com.abc.account.statement.service;

import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.UserRequest;

/**
 * @author Subatra Shankar
 *
 */
public interface AccountStatementService {
	public AccountStatement getAccountStatement(final UserRequest userRequest);
}