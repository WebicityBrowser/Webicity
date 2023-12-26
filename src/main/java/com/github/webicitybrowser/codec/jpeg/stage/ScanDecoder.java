package com.github.webicitybrowser.codec.jpeg.stage;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.JPEGState;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo.SOFComponentInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkInfo.SOSComponentInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkParser;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.ScanComponent;
import com.github.webicitybrowser.codec.jpeg.scan.ScanComponentResult;
import com.github.webicitybrowser.codec.jpeg.scan.ScanParser;
import com.github.webicitybrowser.codec.jpeg.scan.huffman.HuffmanEntropyDecoder;

public final class ScanDecoder {

	private ScanDecoder() {}

	public static byte[] decodeScan(JPEGState jpegState) throws IOException, MalformedJPEGException {
		ScanComponentResult[] scanData = startScan(jpegState);
		int[] scanData1 = scanData[0].data();
		return decodeData(scanData1, jpegState);
	}
	
	private static ScanComponentResult[] startScan(JPEGState jpegState) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = jpegState.dataStream();
		SOSChunkInfo sosChunkInfo = SOSChunkParser.read(dataStream);
		SOSComponentInfo[] sosComponents = sosChunkInfo.components();
		SOFComponentInfo[] sofComponents =jpegState.sofChunkInfo().components();
		ScanComponent[] scanComponents = new ScanComponent[sosComponents.length];
		for (int i = 0; i < sosComponents.length; i++) {
			SOSComponentInfo sosComponent = sosComponents[i];
			int componentId = sosComponent.componentId();
			SOFComponentInfo sofComponent = findSOFComponent(sofComponents, componentId);
			// TODO: We don't know if we will actually use huffman
			EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(
				jpegState.getDCHuffmanTable(sosComponent.dcCodingTableSelector()),
				jpegState.getACHuffmanTable(sosComponent.acCodingTableSelector()));
			scanComponents[i] = new ScanComponent(
				componentId, entropyDecoder,
				sofComponent.horizontalSamplingFactor(), sofComponent.verticalSamplingFactor());
		}

		return ScanParser.read(dataStream, scanComponents);
	}

	private static SOFComponentInfo findSOFComponent(SOFComponentInfo[] sofComponents, int componentId) {
		for (SOFComponentInfo sofComponent : sofComponents) {
			if (sofComponent.componentId() == componentId) {
				return sofComponent;
			}
		}
		
		throw new IllegalArgumentException("Could not find component with id: " + componentId);
	}

	private static byte[] decodeData(int[] scanData, JPEGState jpegState) {
		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		byte[] completedImage = new byte[sofChunkInfo.width() * sofChunkInfo.height() * 4];
		for (int x = 0; x < sofChunkInfo.width(); x += 8) {
			for (int y = 0; y < sofChunkInfo.height(); y += 8) {
				decodeDataBlock(scanData, jpegState, completedImage, x, y);
			}
		}

		return completedImage;
	}

	private static void decodeDataBlock(int[] scanData, JPEGState jpegState, byte[] completedImage, int x, int y) {
		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		DQTChunkInfo dqtChunkInfo = jpegState.dqtChunkInfo();
		int[] quantizationTable = ZigZagDecoder.decode(dqtChunkInfo.tables()[0], 0);
		int blockOffset = x * 8 + y * sofChunkInfo.width();
		int[] cells = ZigZagDecoder.decode(scanData, blockOffset);
		int[] decoded = DCTDecoder.decodeDCT(cells, quantizationTable);
		for (int i = 0; i < 64; i++) {
			int cell = decoded[i];
			int cellX = i % 8;
			int cellY = i / 8;
			int imgX = x + cellX;
			int imgY = y + cellY;
			if (imgX >= sofChunkInfo.width() || imgY >= sofChunkInfo.height()) {
				continue;
			}
			int pixelOffset = imgX * 4 + imgY * sofChunkInfo.width() * 4;
			
			cell += 128;
			cell = Math.max(0, Math.min(255, cell));
			completedImage[pixelOffset] = (byte) cell;
			completedImage[pixelOffset + 1] = (byte) cell;
			completedImage[pixelOffset + 2] = (byte) cell;
			completedImage[pixelOffset + 3] = (byte) 255;
		}
	}

}
