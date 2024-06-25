package com.github.webicitybrowser.codec.jpeg.scan.primitivecollection;

public class BitStream {

	private final byte[] data;

	private int pointer = 0;

	public BitStream(byte[] data) {
		this.data = data;
	}

	public int readBit() {
		int bit = (data[pointer / 8] >>> (7 - (pointer % 8))) & 0x01;
		pointer++;
		return bit;
	}

	public int remaining() {
		return data.length * 8 - pointer;
	}

	public int peek(int index) {
		int bit = (data[(pointer + index) / 8] >>> (7 - ((pointer + index) % 8))) & 0x01;
		return bit;
	}

}
