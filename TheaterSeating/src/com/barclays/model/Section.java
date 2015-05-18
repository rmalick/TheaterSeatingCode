package com.barclays.model;
/**
 * 
 * @author ranjit_malick 
 * 
 */
public class Section {

	private int rowIndex;
	private int index;
	private int remainingSize;
	private int totalSize;

	public Section(int index, int rowIndex, int size) {
		this.index = index;
		this.remainingSize = size;
		this.rowIndex = rowIndex;
		this.totalSize = size;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getIndex() {
		return index;
	}

	public Integer getTotalSize() {
		return this.totalSize;
	}

	public int getRemainingSize() {
		return remainingSize;
	}

	public void setRemainingSize(int remainingSize) {
		this.remainingSize = remainingSize;
	}

}
