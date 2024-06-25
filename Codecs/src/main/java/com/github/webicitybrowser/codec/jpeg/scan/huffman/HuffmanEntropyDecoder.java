package com.github.webicitybrowser.codec.jpeg.scan.huffman;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.BitStream;

/**
 * The huffman entropy decoder is a {@link EntropyDecoder} that uses
 * a mixture of huffman decoding and run-length decoding paired with a
 * special following-bits decoding to decode the huffman entropy encoded data.
 */
public class HuffmanEntropyDecoder implements EntropyDecoder {


	private final DHTBinaryTree dcBinaryTree;
	private final DHTBinaryTree acBinaryTree;

	private int lastDCValue = 0;

	public HuffmanEntropyDecoder(DHTBinaryTree dcBinaryTree, DHTBinaryTree acBinaryTree) {
		this.dcBinaryTree = dcBinaryTree;
		this.acBinaryTree = acBinaryTree;
	}

	@Override
	public int[] readBlock(BitStream bitStream) {
		int[] decodedBytes = new int[64];
		int bitCount = decodeHuffmanValue(bitStream, dcBinaryTree);
		decodedBytes[0] = readNumber(bitStream, bitCount) + lastDCValue;
		lastDCValue = decodedBytes[0];

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

		return decodedBytes;
	}

	@Override
	public void restart() {
		lastDCValue = 0;
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
		boolean isNegative = bitStream.readBit()  == 0;
		for (int i = 0; i < bitCount - 1; i++) {
			value = (value << 1) | (!isNegative ? bitStream.readBit() : bitStream.readBit() ^ 1);
		}

		value += 1 << (bitCount - 1);
		value *= isNegative ? -1 : 1;

		return value;
	}
	
}
