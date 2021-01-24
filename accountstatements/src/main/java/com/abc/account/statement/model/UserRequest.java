/**
 * 
 */
package com.abc.account.statement.model;

import java.io.Serializable;

/**
 * @author Subatra Shankar
 * POJO to hold the user request.
 */
public class UserRequest implements Serializable {

	private static final long serialVersionUID = -6319655529878887783L;
	private Integer id;
	private String fromDate;
	private String toDate;
	private Double fromAmount;
	private Double toAmount;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(final Double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public Double getToAmount() {
		return toAmount;
	}

	public void setToAmount(final Double toAmount) {
		this.toAmount = toAmount;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
