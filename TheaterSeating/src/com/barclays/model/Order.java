package com.barclays.model;
/**
 * 
 * @author ranjit_malick 
 * 
 */
public class Order {
	private String partyName;
	private Integer requiredSeatCount;
	private Integer index;

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Order))
			return false;
		Order thatObj = (Order) obj;
		if (index == thatObj.getIndex()
				&& getPartyName().equals(thatObj.getPartyName())
				&& requiredSeatCount == thatObj.getRequiredSeatCount())
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		int hashCode = partyName.hashCode() + requiredSeatCount * 10 + index
				* 12;
		return hashCode;
	}

	public Order(Integer index, String partyName, Integer requiredSeatCount) {
		this.index = index;
		this.partyName = partyName;
		this.requiredSeatCount = requiredSeatCount;
	}

	public String getPartyName() {
		return partyName;
	}

	public Integer getRequiredSeatCount() {
		return requiredSeatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.requiredSeatCount = seatCount;
	}

	@Override
	public String toString() {
		return getPartyName() + " " + getRequiredSeatCount();
	}

	public Integer getIndex() {
		return index;
	}
}
