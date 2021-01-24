/**
 * 
 */
package com.abc.account.statement.service;

import java.util.List;

import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.UserRequest;

/**
 * @author Subatra Shankar
 *
 */
public interface AccountStatementService {
	public List<AccountStatement> getAccountStatement(final UserRequest userRequest);
}