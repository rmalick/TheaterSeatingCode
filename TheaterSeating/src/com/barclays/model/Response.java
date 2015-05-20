package com.barclays.model;

import java.io.Serializable;
/**
 * @author ranjit_malick
 */

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private Order order;
	private Section section;
	private String mesg;
	private Integer rowIndex;
	private ResponseEnum responseEnum;

	public Response(Order order, Section section, Integer rowIndex) {
		this.order = order;
		this.section = section;
		this.rowIndex = rowIndex;
	}

	public Response(Order order, ResponseEnum responseEnum) {
		this.order = order;
		this.section = null;
		this.rowIndex = null;
		this.responseEnum = responseEnum;
	}

	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}

	@Override
	public String toString() {
		if (this.responseEnum != null)
			return order.getPartyName() + " " + responseEnum.getMessage();
		else
			return order.getPartyName() + " Row " + rowIndex + " Section "
					+ section.getIndex();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}
}
