/**
 * 
 */
package com.abc.account.statement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Subatra Shankar
 * Entity class corresponding to the statement table in the DB.
 */
@Entity
@Table(name = "statement")
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account_id")
	private Integer accountId;
	private String datefield;
	private String amount;

	public Statement() {
		// constructor
	}

	public Statement(String datefield, String amount) {
		super();
		this.datefield = datefield;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Statement [id=" + id + ", accountId=" + accountId + ", datefield=" + datefield + ", amount=" + amount
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDatefield() {
		return datefield;
	}

	public void setDatefield(String datefield) {
		this.datefield = datefield;
	}
}
