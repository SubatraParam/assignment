package com.abc.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abc.account.model.ErrorMessage;

/**
 * @author Subatra Shankar
 *  Custom exception handler class to handle various
 *  exceptions that occur in the application.
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { InvalidInputException.class })
	public ResponseEntity<ErrorMessage> handleInvalidInputException(InvalidInputException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), "Invalid input.");
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorMessage> handleException(Exception ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), "Something went wrong! Please try again later.");
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
