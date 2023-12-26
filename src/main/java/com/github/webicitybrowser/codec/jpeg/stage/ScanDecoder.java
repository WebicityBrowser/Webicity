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
		return decodeData(scanData, jpegState);
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
				sofComponent.quantizationTableSelector(),
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

	private static ScanComponentResult findScanComponentResult(ScanComponentResult[] scanData, int componentId) {
		for (ScanComponentResult scanComponentResult : scanData) {
			if (scanComponentResult.componentId() == componentId) {
				return scanComponentResult;
			}
		}
		
		throw new IllegalArgumentException("Could not find component with id: " + componentId);
	}

	private static byte[] decodeData(ScanComponentResult[] scanData, JPEGState jpegState) {
		ScanComponentResult[] adjustedScanData = adjustScanData(scanData);

		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		byte[] completedImage = new byte[sofChunkInfo.width() * sofChunkInfo.height() * 4];
		for (int x = 0; x < sofChunkInfo.width(); x += 8) {
			for (int y = 0; y < sofChunkInfo.height(); y += 8) {
				decodeDataBlock(adjustedScanData, jpegState, completedImage, x, y);
			}
		}

		return completedImage;
	}

	private static ScanComponentResult[] adjustScanData(ScanComponentResult[] scanData) {
		return scanData.length == 1 ?
			new ScanComponentResult[] { findScanComponentResult(scanData, 1) } :
			new ScanComponentResult[] {
				findScanComponentResult(scanData, 1),
				findScanComponentResult(scanData, 2),
				findScanComponentResult(scanData, 3)
			};
	}

	private static void decodeDataBlock(ScanComponentResult[] scanData, JPEGState jpegState, byte[] completedImage, int x, int y) {
		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		int blockOffset = x * 8 + y * sofChunkInfo.width();

		int[][] componentBlocks = new int[scanData.length][];
		for (int i = 0; i < 3; i++) {
			componentBlocks[i] = decodeComponentBlock(scanData[i], jpegState, blockOffset);
		}

		for (int i = 0; i < 64; i++) {
			int cellX = i % 8;
			int cellY = i / 8;
			int imgX = x + cellX;
			int imgY = y + cellY;
			if (imgX >= sofChunkInfo.width() || imgY >= sofChunkInfo.height()) {
				continue;
			}
			int pixelOffset = imgX * 4 + imgY * sofChunkInfo.width() * 4;
			
			placePixel(componentBlocks, completedImage, pixelOffset, i);
		}
	}

	private static void placePixel(int[][] componentBlocks, byte[] completedImage, int pixelOffset, int blockOffset) {
		completedImage[pixelOffset + 3] = (byte) 255;
		int Y = componentBlocks[0][blockOffset] + 128;
		if (componentBlocks.length == 1) {
			for (int i = 0; i < 3; i++) {
				completedImage[pixelOffset + i] = clampToByte(Y);
			}
		} else {
			int Cb = componentBlocks[1][blockOffset] + 128;
			int Cr = componentBlocks[2][blockOffset] + 128;
			completedImage[pixelOffset + 0] = clampToByte(Y + 1.402 * (Cr - 128));
			completedImage[pixelOffset + 1] = clampToByte(Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
			completedImage[pixelOffset + 2] = clampToByte(Y + 1.772 * (Cb - 128));
		}
	}

	private static int[] decodeComponentBlock(ScanComponentResult scanComponentResult, JPEGState jpegState, int blockOffset) {
		DQTChunkInfo dqtChunkInfo = jpegState.dqtChunkInfo();
		int[] quantizationTable = ZigZagDecoder.decode(
			dqtChunkInfo.tables()[scanComponentResult.quantizationTableId()], 0);
		int[] cells = ZigZagDecoder.decode(scanComponentResult.data(), blockOffset);
		return DCTDecoder.decodeDCT(cells, quantizationTable);
	}

	private static byte clampToByte(double value) {
		return (byte) Math.max(0, Math.min(255, value));
	}

}
