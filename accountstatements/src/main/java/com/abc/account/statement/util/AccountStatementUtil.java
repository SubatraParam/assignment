package com.abc.account.statement.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

import com.abc.account.exception.InvalidInputException;
import com.abc.account.statement.constant.AccountStatementConstants;
import com.abc.account.statement.entity.Statement;

public final class AccountStatementUtil {
	public static String hashAccountNumber(final String accountNumber) {
		return accountNumber.replaceAll(".(?=.{4})", "X");
	}

	public static void validateUserRequestedDates(final String fromDate, final String toDate)
			throws InvalidInputException {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AccountStatementConstants.DD_MM_YYYY,
				Locale.ENGLISH);
		if (LocalDate.parse(fromDate, formatter).isAfter(LocalDate.parse(toDate, formatter))) {
			throw new InvalidInputException(
					"The fromdate cannot be after todate value. Please enter valid dates to proceed.");
		}
	}

	public static void validateUserRequestedAmountValues(final Double fromAmount, final Double toAmount)
			throws InvalidInputException {
		if (fromAmount.compareTo(toAmount) >= 0) {
			throw new InvalidInputException(
					"The fromamount cannot be greater than toamount value. Please enter valid amount values to proceed.");
		}
	}
	
	public static Comparator<Statement> dateComparator = new Comparator<Statement>() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);

		@Override
		public int compare(Statement s1, Statement s2) {
			LocalDate d1 = LocalDate.parse(s1.getDatefield(), formatter);
			LocalDate d2 = LocalDate.parse(s2.getDatefield(), formatter);
			return d2.compareTo(d1);
		}
	};
}
