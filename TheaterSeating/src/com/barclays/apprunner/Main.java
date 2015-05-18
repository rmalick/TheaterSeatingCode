package com.barclays.apprunner;

import java.io.File;

import com.barclays.model.TheaterLayout;
import com.barclays.seat.SeatAssignment;
import com.barclays.seat.SeatAssignmentImpl;
import com.barclays.util.TheaterLayoutUtil;
/**
 * 
 * @author ranjit_malick  
 */
public class Main {
	public static void main(String[] args) {

		File layoutFile = new File("src/layoutTest.txt");

		TheaterLayout layout = TheaterLayoutUtil.createLayout(layoutFile);

		SeatAssignment assignment = new SeatAssignmentImpl(layout);
		assignment.startAssignment();
		assignment.generateResponse();

	}
}
