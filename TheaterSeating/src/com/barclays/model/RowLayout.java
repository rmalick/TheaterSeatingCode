package com.barclays.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.exception.InvalidSectionSizeException;

/**
 * @author ranjit_malick
 */
public class RowLayout implements Serializable {

	private static final long serialVersionUID = 1L;
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
			try {
				int sectionSize = Integer.parseInt(section);
				if (sectionSize < 0)
					throw new InvalidSectionSizeException(
							String.valueOf(sectionSize));
				setTotalSeat(totalSeat + sectionSize);
				if (sectionSize > maxSectionSize)
					maxSectionSize = sectionSize;
				sectionList.add(new Section(sectionIndex++, rowIndex,
						sectionSize));
			} catch (NumberFormatException exception) {
				throw new InvalidSectionSizeException(section);
			}
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
