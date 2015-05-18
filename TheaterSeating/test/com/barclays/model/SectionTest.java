package com.barclays.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SectionTest {

	@Test
	public void test() {
		Section section = new Section(1, 1, 12);
		assertTrue(12 == section.getRemainingSize());
		assertFalse(10 == section.getRemainingSize());
	}

}
