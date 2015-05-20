package com.barclays.model;
/**
 * @author ranjit_malick 
 */
public enum ResponseEnum {
	CANT_ASSIGN("Sorry, we can't handle your party."), SPLIT_TO_CALL("Call to split party.");
	String message;

	public String getMessage() {
		return message;
	}

	private ResponseEnum(String message) {
		this.message = message;
	}
}