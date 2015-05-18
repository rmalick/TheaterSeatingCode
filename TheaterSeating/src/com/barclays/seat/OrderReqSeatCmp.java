package com.barclays.seat;

import java.util.Comparator;

import com.barclays.model.Order;

/**
 * Comparator for Orders
 */
public enum OrderReqSeatCmp implements Comparator<Order> {

	ASC_ORDER("asc"), DESC_ORDER("desc");
	String name;

	OrderReqSeatCmp(String name) {
		this.name = name;
	}

	public int compareAsc(Order o1, Order o2) {
		int result = 0;
		if (o1.getRequiredSeatCount() > o2.getRequiredSeatCount()) {
			result = 1;
		} else if (o1.getRequiredSeatCount() == o2.getRequiredSeatCount()) {
			if (o1.getIndex() < o2.getIndex()) {
				result = -1;
			} else {
				result = 1;
			}
		} else if (o1.getRequiredSeatCount() < o2.getRequiredSeatCount()) {
			result = -1;
		}
		return result;
	}

	public int compareDesc(Order o1, Order o2) {
		int result = 0;
		if (o1.getRequiredSeatCount() > o2.getRequiredSeatCount()) {
			result = -1;
		} else if (o1.getRequiredSeatCount() == o2.getRequiredSeatCount()) {
			if (o1.getIndex() < o2.getIndex()) {
				result = -1;
			} else {
				result = 1;
			}
		} else if (o1.getRequiredSeatCount() < o2.getRequiredSeatCount()) {
			result = 1;
		}
		return result;
	}

	@Override
	public int compare(Order o1, Order o2) {
		if (name.equals("asc"))
			return compareAsc(o1, o2);
		else
			return compareDesc(o1, o2);
	}
}
