package com.github.webicitybrowser.codec.jpeg;

import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;

public class JPEGState {

	private final PushbackInputStream dataStream;

	private DHTBinaryTree[] dcHuffmanTables = new DHTBinaryTree[4];
	private DHTBinaryTree[] acHuffmanTables = new DHTBinaryTree[4];
	private int[][] dqtTables = new int[4][];
	private SOFChunkInfo sofChunkInfo;
	private byte[] completedImage;

	public JPEGState(PushbackInputStream dataStream) {
		this.dataStream = dataStream;
	}

	public void installDCHuffmanTable(int tableId, DHTBinaryTree tree) {
		dcHuffmanTables[tableId] = tree;
	}

	public void installACHuffmanTable(int tableId, DHTBinaryTree tree) {
		acHuffmanTables[tableId] = tree;
	}

	public void installQuantizationTable(int id, int[] table) {
		dqtTables[id] = table;
	}

	public void setSOFChunkInfo(SOFChunkInfo sofChunkInfo) {
		this.sofChunkInfo = sofChunkInfo;
	}

	public void setCompletedImage(byte[] completedImage) {
		this.completedImage = completedImage;
	}

	public PushbackInputStream dataStream() {
		return this.dataStream;
	}

	public DHTBinaryTree getDCHuffmanTable(int id) {
		return this.dcHuffmanTables[id];
	}

	public DHTBinaryTree getACHuffmanTable(int id) {
		return this.acHuffmanTables[id];
	}

	public int[] getQuantizationTable(int id) {
		return this.dqtTables[id];
	}

	public SOFChunkInfo sofChunkInfo() {
		return this.sofChunkInfo;
	}

	public byte[] completedImage() {
		return this.completedImage;
	}

}
