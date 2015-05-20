package com.barclays.model;

import java.io.Serializable;

/**
 * @author ranjit_malick
 */
public class Section implements Serializable {

	private static final long serialVersionUID = 1L;
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
