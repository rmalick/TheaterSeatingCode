package com.barclays.seat;

public interface SeatAssignment {

	/**
	 * call this method to start assigning seats to the people
	 */
	void startAssignment();

	/**
	 * Generates response from theater layout
	 */
	void generateResponse();
}
