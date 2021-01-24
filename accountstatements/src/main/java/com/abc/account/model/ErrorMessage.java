package com.abc.account.model;

import java.io.Serializable;

/**
 * @author Subatra Shankar
 * ErrorMessage class to populate and display the error/exception that
 * has occurred while accessing the APIs.
 */
public class ErrorMessage implements Serializable{
	private static final long serialVersionUID = -1875095695735251622L;
	private String message;
	private String errorDetails;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorMessage(String message, String errorDetails) {
		super();
		this.message = message;
		this.errorDetails = errorDetails;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

}
