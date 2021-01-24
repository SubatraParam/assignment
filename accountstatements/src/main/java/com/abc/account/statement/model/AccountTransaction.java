/**
 * 
 */
package com.abc.account.statement.model;

import java.io.Serializable;

/**
 * @author Subatra Shankar
 * POJO holding the account transaction details to be populated in the response for user request.
 */
public class AccountTransaction implements Serializable {

	private static final long serialVersionUID = -835824937780756926L;
	private String date;
	private String amount;

	public String getAmount() {
		return amount;
	}

	public void setAmount(final String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public AccountTransaction(final String date, final String amount) {
		super();
		this.date = date;
		this.amount = amount;
	}

}
