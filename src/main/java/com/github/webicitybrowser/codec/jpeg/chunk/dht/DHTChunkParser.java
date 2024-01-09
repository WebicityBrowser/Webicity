package com.github.webicitybrowser.codec.jpeg.chunk.dht;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class DHTChunkParser {
	
	private DHTChunkParser() {}

	public static DHTChunkInfo read(InputStream inputStream) throws IOException, MalformedJPEGException {
		int remainingLength = JPEGUtil.readTwoByte(inputStream) - 2;
		DHTBinaryTree[] dcHuffmanTables = new DHTBinaryTree[4];
		DHTBinaryTree[] acHuffmanTables = new DHTBinaryTree[4];
		while (remainingLength > 0) {
			TableResult tableResult = readTable(inputStream);
			DHTBinaryTree table = createHuffmanTable(tableResult);
			if (tableResult.isLossless()) {
				dcHuffmanTables[tableResult.destination()] = table;
			} else {
				acHuffmanTables[tableResult.destination()] = table;
			}
			remainingLength -= tableResult.numValues();
			remainingLength -= 17;
		}

		return new DHTChunkInfo(dcHuffmanTables, acHuffmanTables);
	}

	private static TableResult readTable(InputStream inputStream) throws IOException, MalformedJPEGException {
		int numValues = 0;
		int tableInfo = JPEGUtil.read(inputStream);
		boolean isLossless = (tableInfo & 0x10) == 0;
		int destination = tableInfo & 0x0F;
		int[] lengths = new int[16];
		for (int i = 0; i < 16; i++) {
			lengths[i] = JPEGUtil.read(inputStream);
			numValues += lengths[i];
		}
		int[] values = new int[numValues];
		int j = 0;
		for (int i = 0; i < 16; i++) {
			for (int k = 0; k < lengths[i]; k++) {
				values[j++] = JPEGUtil.read(inputStream);
			}
		}

		return new TableResult(numValues, isLossless, destination, lengths, values);
	}

	private static DHTBinaryTree createHuffmanTable(TableResult tableResult) {
		int[] huffmanSizes = createHuffmanSizes(tableResult);
		int[] huffmanCodes = createHuffmanCodes(tableResult, huffmanSizes);
		int[] huffmanValues = tableResult.values();
		DHTBinaryTree root = new DHTBinaryTree(-1, null, null);
		for (int i = 0; i < tableResult.numValues(); i++) {
			root = insertNode(root, huffmanCodes[i], huffmanSizes[i], huffmanValues[i]);
		}

		return root;
	}

	private static DHTBinaryTree insertNode(DHTBinaryTree node, int key, int depth, int insertedValue) {
		if (depth == 0) return new DHTBinaryTree(insertedValue, null, null);
		if (node == null) node = new DHTBinaryTree(-1, null, null);
		if ((key & (1 << depth - 1)) == 0) {
			return new DHTBinaryTree(node.value(), insertNode(node.left(), key, depth - 1, insertedValue), node.right());
		} else {
			return new DHTBinaryTree(node.value(), node.left(), insertNode(node.right(), key, depth - 1, insertedValue));
		}
	}

	private static int[] createHuffmanSizes(TableResult tableResult) {
		int[] huffmanSizes = new int[tableResult.numValues()];
		int j = 0;
		for (int i = 0; i < 16; i++) {
			for (int k = 0; k < tableResult.lengths()[i]; k++) {
				huffmanSizes[j++] = i + 1;
			}
		}

		return huffmanSizes;
	}

	private static int[] createHuffmanCodes(TableResult tableResult, int[] huffmanSizes) {
		int[] huffmanCodes = new int[tableResult.numValues()];
		int code = 0;
		int lastSize = 0;
		for (int i = 0; i < tableResult.numValues(); i++) {
			code = (code + 1) << (huffmanSizes[i] - lastSize);
			lastSize = huffmanSizes[i];
			huffmanCodes[i] = code;
		}

		return huffmanCodes;
	}

	public static record DHTBinaryTree(int value, DHTBinaryTree left, DHTBinaryTree right) {}
	private static record TableResult(int numValues, boolean isLossless, int destination, int[] lengths, int[] values) {}
}
