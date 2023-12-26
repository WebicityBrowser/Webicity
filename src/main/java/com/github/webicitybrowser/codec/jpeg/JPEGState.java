package com.github.webicitybrowser.codec.jpeg;

import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;

public class JPEGState {

	private final PushbackInputStream dataStream;

	private DHTBinaryTree[] dcHuffmanTables = new DHTBinaryTree[4];
	private DHTBinaryTree[] acHuffmanTables = new DHTBinaryTree[4];
	private DQTChunkInfo dqtChunkInfo;
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

	public void setSOFChunkInfo(SOFChunkInfo sofChunkInfo) {
		this.sofChunkInfo = sofChunkInfo;
	}

	public void setDQTChunkInfo(DQTChunkInfo dqtChunkInfo) {
		this.dqtChunkInfo = dqtChunkInfo;
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

	public SOFChunkInfo sofChunkInfo() {
		return this.sofChunkInfo;
	}

	public DQTChunkInfo dqtChunkInfo() {
		return this.dqtChunkInfo;
	}

	public byte[] completedImage() {
		return this.completedImage;
	}

}
