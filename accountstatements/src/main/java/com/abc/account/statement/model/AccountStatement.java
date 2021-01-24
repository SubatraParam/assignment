/**
 * 
 */
package com.abc.account.statement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Subatra Shankar
 * POJO to populate the requested Account statement to the user.
 */
public class AccountStatement extends ResponseMessage implements Serializable {
	public AccountStatement(final int accountId, final String accountNumber, final String accountType,
			final List<AccountTransaction> transactionDetails) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.transactionDetails = transactionDetails;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3960026580080281660L;
	@Override
	public String toString() {
		return "AccountStatement [accountId=" + accountId + ", accountNumber=" + accountNumber + ", accountType="
				+ accountType + ", transactionDetails=" + transactionDetails + "]";
	}

	private int accountId;
	private String accountNumber;
	private String accountType;
	private List<AccountTransaction> transactionDetails;

	public AccountStatement() {
		// constructor
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(final int accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(final String accountType) {
		this.accountType = accountType;
	}

	public List<AccountTransaction> getTransactionDetails() {
		if (null == transactionDetails) {
			transactionDetails = new ArrayList<>();
		}
		return transactionDetails;
	}

	public void setTransactionDetails(final List<AccountTransaction> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}
