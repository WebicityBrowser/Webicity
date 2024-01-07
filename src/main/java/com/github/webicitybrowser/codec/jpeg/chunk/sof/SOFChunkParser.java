package com.github.webicitybrowser.codec.jpeg.chunk.sof;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo.SOFComponentInfo;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class SOFChunkParser {
	
	private SOFChunkParser() {}

	public static SOFChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int remainingLength = JPEGUtil.readTwoByte(chunkSection) - 2;
		if (remainingLength < 9 || (remainingLength - 9) % 3 != 0) {
			throw new MalformedJPEGException("SOF chunk length mismatch");
		}

		JPEGUtil.read(chunkSection); // precision
		int height = JPEGUtil.readTwoByte(chunkSection);
		int width = JPEGUtil.readTwoByte(chunkSection);
		
		int componentCount = JPEGUtil.read(chunkSection);
		SOFComponentInfo[] componentInfos = new SOFComponentInfo[componentCount];
		for (int i = 0; i < componentCount; i++) {
			int componentId = JPEGUtil.read(chunkSection);
			int samplingFactors = JPEGUtil.read(chunkSection);
			int quantizationTableId = JPEGUtil.read(chunkSection);
			int horizontalSamplingFactor = samplingFactors >>> 4;
			int verticalSamplingFactor = samplingFactors & 0x0F;
			componentInfos[i] = new SOFComponentInfo(
				componentId, horizontalSamplingFactor, verticalSamplingFactor, quantizationTableId
			);
		}

		return new SOFChunkInfo(width, height, componentInfos);
	}

}
