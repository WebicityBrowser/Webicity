package com.github.webicitybrowser.codec.jpeg.scan.primitivecollection;

public class ByteArray {
	
	private byte[] data = new byte[64];
	private int size = 0;

	public void add(byte value) {
		if (size >= data.length) {
			byte[] newData = new byte[data.length * 2];
			System.arraycopy(data, 0, newData, 0, data.length);
			data = newData;
		}
		data[size++] = value;
	}

	public byte[] toArray() {
		byte[] result = new byte[size];
		System.arraycopy(data, 0, result, 0, size);
		return result;
	}

	public void clear() {
		size = 0;
	}

}
