package com.barclays.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {
	Order order = new Order(0, "Sabya", 10);

	@Test
	public void OrderDescTest() {
		String expectedOrderDesc = "Sabya 10";

		assertEquals(expectedOrderDesc, order.toString());
	}

	@Test
	public void toStringTest() {

		assertTrue(order.equals(order));

	}

}
