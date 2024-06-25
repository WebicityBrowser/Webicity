package com.github.webicitybrowser.codec.jpeg.chunk.dht;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;

public record DHTChunkInfo(DHTBinaryTree[] dcHuffmanTables, DHTBinaryTree[] acHuffmanTables) {
	
}
