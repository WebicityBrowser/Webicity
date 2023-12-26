package com.github.webicitybrowser.codec.jpeg.scan.primitivecollection;

public class IntArray {
	
	private int[] data = new int[64];
	private int size = 0;

	public void add(int value) {
		if (size >= data.length) {
			int[] newData = new int[data.length * 2];
			System.arraycopy(data, 0, newData, 0, data.length);
			data = newData;
		}
		data[size++] = value;
	}

	public void add(int[] values) {
		if (size + values.length >= data.length) {
			int[] newData = new int[data.length * 2];
			System.arraycopy(data, 0, newData, 0, data.length);
			data = newData;
		}
		System.arraycopy(values, 0, data, size, values.length);
		size += values.length;
	}

	public int[] toArray() {
		int[] result = new int[size];
		System.arraycopy(data, 0, result, 0, size);
		return result;
	}

	public int size() {
		return size;
	}

}
