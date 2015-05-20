package com.barclays.exception;

public class OrderFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OrderFormatException() {
		super("Invalid order format. Please correct layout file");
	}

}
