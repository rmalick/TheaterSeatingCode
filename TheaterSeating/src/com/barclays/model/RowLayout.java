package com.barclays.model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author ranjit_malick 
 * 
 */
public class RowLayout {

	private String name;
	private List<Section> sectionList;
	private int totalSeat = 0;
	private int maxSectionSize = 0;

	public int getMaxSectionSize() {
		return maxSectionSize;
	}

	public void setMaxSectionSize(int maxSectionSize) {
		this.maxSectionSize = maxSectionSize;
	}

	public RowLayout(int rowIndex, String[] sections) {
		setName("Row " + rowIndex + " ");
		sectionList = new ArrayList<>();
		int sectionIndex = 1;
		for (String section : sections) {
			int sectionSize = Integer.parseInt(section);
			setTotalSeat(totalSeat + sectionSize);
			if (sectionSize > maxSectionSize)
				maxSectionSize = sectionSize;
			sectionList.add(new Section(sectionIndex++, rowIndex, sectionSize));
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Section section : sectionList) {
			builder.append(section.getRemainingSize()).append(" ");
		}
		return builder.toString();
	}

	public int getTotalSeat() {
		return totalSeat;
	}

	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Section> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}

}
