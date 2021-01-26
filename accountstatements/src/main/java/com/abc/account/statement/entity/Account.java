/**
 * 
 */
package com.abc.account.statement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Subatra Shankar
 * Entity class corresponding to the account table in the DB.
 */
@Entity
@Table(name = "account")
public class Account {

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountType=" + accountType + ", accountNumber=" + accountNumber
				+ ", transactions=" + transactions + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "account_type")
	private String accountType;
	@Column(name = "account_number")
	private String accountNumber;

	@OneToMany  (cascade = CascadeType.ALL) 
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private List<Statement> transactions = new ArrayList<>();

	public Account() {
	}

	public Account(String accountType, String accountNumber, List<Statement> transactions) {
		super();
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.transactions = transactions;
	}

	public List<Statement> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Statement> transactions) {
		this.transactions = transactions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
