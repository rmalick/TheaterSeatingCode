package com.barclays.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ranjit_malick
 */
public class TheaterLayout {

	private Map<Integer, RowLayout> rowLayoutMap = new LinkedHashMap<Integer, RowLayout>();
	private Map<Integer, Order> orderMap = new LinkedHashMap<Integer, Order>();

	private int toltalRemainingSeats = 0;
	private int maxSectionSize = 0;

	/**
	 * 
	 * @return theater layout
	 */
	public Map<Integer, RowLayout> getRowLayoutMap() {
		return rowLayoutMap;
	}

	/**
	 * sets theater layout
	 * 
	 * @param rowLayoutMap
	 */
	public void setRowLayoutMap(Map<Integer, RowLayout> rowLayoutMap) {
		this.rowLayoutMap = rowLayoutMap;
	}

	/**
	 * @return Order map
	 */
	public Map<Integer, Order> getOrderMap() {
		return orderMap;
	}

	public int getMaxSectionSize() {
		return maxSectionSize;
	}

	public void setMaxSectionSize(int maxSectionSize) {
		this.maxSectionSize = maxSectionSize;
	}

	/**
	 * @return total remaining size in the theater
	 */
	public int getToltalRemainingSeats() {
		return toltalRemainingSeats;
	}

	public void setToltalRemainingSeats(int toltalRemainingSeats) {
		this.toltalRemainingSeats = toltalRemainingSeats;
	}
}
