package com.abc.account.statement.util;

public final class AccountStatementUtil {
	public static String hashAccountNumber(final String accountNumber) {
		 return accountNumber.replaceAll(".(?=.{4})", "X");
	}
}
