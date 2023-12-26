package com.github.webicitybrowser.codec.jpeg.chunk.sos;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkInfo.SOSComponentInfo;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class SOSChunkParser {
	
	private SOSChunkParser() {}

	public static SOSChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int length = JPEGUtil.readTwoByte(chunkSection) - 2;
		if (length < 6) throw new MalformedJPEGException("SOS chunk length mismatch");

		int componentCount = JPEGUtil.read(chunkSection);
		SOSComponentInfo[] componentInfos = new SOSComponentInfo[componentCount];

		for (int i = 0; i < componentCount; i++) {
			int componentId = JPEGUtil.read(chunkSection);
			int codingTableSelectors = JPEGUtil.read(chunkSection);
			int dcCodingTableSelector = codingTableSelectors >>> 4;
			int acCodingTableSelector = codingTableSelectors & 0x0F;
			componentInfos[i] = new SOSComponentInfo(componentId, dcCodingTableSelector, acCodingTableSelector);
		}

		// We don't care about the next 3 bytes yet
		for (int i = 0; i < 3; i++) {
			JPEGUtil.read(chunkSection);
		}

		return new SOSChunkInfo(componentInfos);
	}

}
