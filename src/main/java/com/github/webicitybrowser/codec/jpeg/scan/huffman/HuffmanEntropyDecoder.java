package com.github.webicitybrowser.codec.jpeg.scan.huffman;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;

public class HuffmanEntropyDecoder implements EntropyDecoder {


	private final IntArray decodedBytes = new IntArray();

	private final DHTBinaryTree dcBinaryTree;
	private final DHTBinaryTree acBinaryTree;

	private ByteArray initialBytes = new ByteArray();

	private int lastDCValue = 0;

	public HuffmanEntropyDecoder(DHTBinaryTree dcBinaryTree, DHTBinaryTree acBinaryTree) {
		this.dcBinaryTree = dcBinaryTree;
		this.acBinaryTree = acBinaryTree;
	}

	@Override
	public void next(byte value) {
		initialBytes.add(value);
	}

	@Override
	public void done() {
		BitStream bitStream = new BitStream(initialBytes.toArray());
		while (!onlyOnes(bitStream)) {
			readBlock(bitStream);
		}
		initialBytes = new ByteArray();
	}

	@Override
	public int[] getDecoded() {
		return decodedBytes.toArray();
	}

	@Override
	public void restart() {
		done();
		initialBytes = new ByteArray();
		lastDCValue = 0;
	}

	private void readBlock(BitStream bitStream) {
		int[] decodedBytes = new int[64];
		int bitCount = decodeHuffmanValue(bitStream, dcBinaryTree);
		decodedBytes[0] = readNumber(bitStream, bitCount) + lastDCValue;
		lastDCValue = Math.max(Math.min(decodedBytes[0], 128), -128);

		int i = 1;
		while (i < 64) {
			int value = decodeHuffmanValue(bitStream, acBinaryTree);
			if (value == 0) break;

			int zeroCount = value >>> 4;
			bitCount = value & 0x0F;
			if (zeroCount + i + 1 > 64) throw new IllegalStateException("Block length mismatch");
			for (int j = 0; j < zeroCount; j++) decodedBytes[i++] = 0;
			decodedBytes[i++] = (zeroCount == 0xF && bitCount == 0) ? 0 : readNumber(bitStream, bitCount);
		}

		this.decodedBytes.add(decodedBytes);
	}

	private int decodeHuffmanValue(BitStream bitStream, DHTBinaryTree binaryTree) {
		DHTBinaryTree current = binaryTree;
		while (current.value() == -1) {
			current = bitStream.readBit() == 0 ? current.left() : current.right();
			if (current == null) throw new IllegalStateException("Invalid Huffman value");
		}

		return current.value();
	}

	private int readNumber(BitStream bitStream, int bitCount) {
		if (bitCount == 0) return 0;

		int value = 0;
		int multiplier = bitStream.readBit() == 0 ? -1 : 1;
		for (int i = 0; i < bitCount - 1; i++) {
			value = (value << 1) | bitStream.readBit();
		}

		value += 1 << (bitCount - 1);
		value *= multiplier;

		return value;
	}

	private boolean onlyOnes(BitStream bitStream) {
		for (int i = 0; i < bitStream.remaining(); i++) {
			if (bitStream.peek(i) != 1) return false;
		}

		return true;
	}
	
}
