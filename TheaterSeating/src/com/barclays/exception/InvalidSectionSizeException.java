package com.barclays.exception;

public class InvalidSectionSizeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidSectionSizeException(String number) {
		super(number + " is not a valid input for section size. Please correct layout file");
	}

}
