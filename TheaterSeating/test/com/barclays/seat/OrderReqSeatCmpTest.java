package com.barclays.seat;

import static org.junit.Assert.*;

import org.junit.Test;

import com.barclays.model.Order;

public class OrderReqSeatCmpTest {

	@Test
	public void test() {
		Order order1 = new Order(1, "Ranjit", 10);
		Order order2 = new Order(2, "Jone", 10);
		OrderReqSeatCmp asc = OrderReqSeatCmp.ASC_ORDER;
		int i = asc.compare(order1, order1);
		assertEquals(1, i);
	}
}
