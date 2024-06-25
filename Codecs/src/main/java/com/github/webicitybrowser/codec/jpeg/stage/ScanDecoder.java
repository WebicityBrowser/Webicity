package com.github.webicitybrowser.codec.jpeg.stage;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.JPEGState;
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
		return decodeData(jpegState, scanData);
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
			if (scanComponentResult.component().componentId() == componentId) {
				return scanComponentResult;
			}
		}
		
		throw new IllegalArgumentException("Could not find component with id: " + componentId);
	}

	private static byte[] decodeData(JPEGState jpegState, ScanComponentResult[] scanData) {
		int maxHorizontalSamplingFactor = determineMaxHorizontalSamplingFactor(scanData);
		int maxVerticalSamplingFactor = determineMaxVerticalSamplingFactor(scanData);

		ScanComponentResult[] adjustedScanData = adjustScanData(scanData);
		int[][] scanLayer = new int[adjustedScanData.length][];
		for (int i = 0; i < adjustedScanData.length; i++) {
			scanLayer[i] = readScanLayer(jpegState, adjustedScanData[i], maxHorizontalSamplingFactor, maxVerticalSamplingFactor);
		}

		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		byte[] completedImage = adjustedScanData.length == 1 ?
			mergeGrayScale(scanLayer[0], sofChunkInfo.width(), sofChunkInfo.height()) :
			mergeColor(scanLayer, sofChunkInfo.width(), sofChunkInfo.height());

		return completedImage;
	}

	private static int determineMaxHorizontalSamplingFactor(ScanComponentResult[] scanData) {
		int maxHorizontalSamplingFactor = 0;
		for (ScanComponentResult scanComponentResult : scanData) {
			maxHorizontalSamplingFactor = Math.max(maxHorizontalSamplingFactor, scanComponentResult.component().hSample());
		}

		return maxHorizontalSamplingFactor;
	}

	private static int determineMaxVerticalSamplingFactor(ScanComponentResult[] scanData) {
		int maxVerticalSamplingFactor = 0;
		for (ScanComponentResult scanComponentResult : scanData) {
			maxVerticalSamplingFactor = Math.max(maxVerticalSamplingFactor, scanComponentResult.component().vSample());
		}

		return maxVerticalSamplingFactor;
	}

	private static ScanComponentResult[] adjustScanData(ScanComponentResult[] scanData) {
		return scanData.length == 1 ?
			new ScanComponentResult[] { findScanComponentResult(scanData, 3) } :
			new ScanComponentResult[] {
				findScanComponentResult(scanData, 1),
				findScanComponentResult(scanData, 2),
				findScanComponentResult(scanData, 3)
			};
	}
	
	private static int[] readScanLayer(
		JPEGState jpegState, ScanComponentResult scanComponentResult, int maxHorizontalSamplingFactor, int maxVerticalSamplingFactor
	) {	
		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		int[] scanLayer = new int[sofChunkInfo.width() * sofChunkInfo.height()];
		ScanComponent scanComponent = scanComponentResult.component();
		int horizontalScaling = maxHorizontalSamplingFactor / scanComponent.hSample();
		int verticalScaling = maxVerticalSamplingFactor / scanComponent.vSample();

		int blockOffset = 0;
		// The floating point is necessary to prevent a rounding error
		for (int y = 0; y < sofChunkInfo.height() / (8. * verticalScaling); y += scanComponent.vSample()) {
			for (int x = 0; x < sofChunkInfo.width() / (8. * horizontalScaling); x += scanComponent.hSample()) {
				blockOffset = decodeNextSamples(
					jpegState, scanComponentResult, scanLayer, blockOffset,
					x, y, horizontalScaling, verticalScaling);
			}
		}

		return scanLayer;
	}

	private static int decodeNextSamples(
		JPEGState jpegState, ScanComponentResult scanComponentResult, int[] scanLayer,
		int blockOffset, int x, int y, int horizontalScaling, int verticalScaling
	) {
		ScanComponent scanComponent = scanComponentResult.component();
		for (int yInner = 0; yInner < scanComponent.vSample(); yInner++) {
			for (int xInner = 0; xInner < scanComponent.hSample(); xInner++) {
				decodeDataBlock(jpegState, scanComponentResult, scanLayer, blockOffset, x + xInner, y + yInner, horizontalScaling, verticalScaling);
				blockOffset += 64;
			}
		}

		return blockOffset;
	}

	private static void decodeDataBlock(
		JPEGState jpegState, ScanComponentResult scanComponentResult, int[] scanLayer, int blockOffset,
		int x, int y, int horizontalScaling, int verticalScaling
	) {
		int[] componentBlock = decodeComponentBlock(jpegState, scanComponentResult, blockOffset);

		for (int i = 0; i < 64; i++) {
			copyCellToImage(jpegState, componentBlock, scanLayer, i, x, y, horizontalScaling, verticalScaling);
		}
	}

	private static int[] decodeComponentBlock(JPEGState jpegState, ScanComponentResult scanComponentResult, int blockOffset) {
		int[] quantizationTable = ZigZagDecoder.decode(
			jpegState.getQuantizationTable(scanComponentResult.component().quantizationTableId()), 0);
		int[] cells = ZigZagDecoder.decode(scanComponentResult.data(), blockOffset);
		return DCTDecoder.decodeDCT(cells, quantizationTable);
	}

	private static void copyCellToImage(
		JPEGState jpegState, int[] componentBlock, int[] scanLayer, int i, int x, int y, int horizontalScaling, int verticalScaling
	) {
		int layerWidth = jpegState.sofChunkInfo().width();
		int layerHeight = jpegState.sofChunkInfo().height();

		int cell = componentBlock[i];
		int cellX = i % 8;
		int cellY = i / 8;
		int imgX = (x * 8 + cellX) * horizontalScaling;
		int imgY = (y * 8 + cellY) * verticalScaling;

		for (int j = 0; j < horizontalScaling; j++) {
			for (int k = 0; k < verticalScaling; k++) {
				int destX = imgX + j;
				int destY = imgY + k;
				if (destX >= layerWidth || destY >= layerHeight) continue;
				int destPixelOffset = destX + destY * layerWidth;
				scanLayer[destPixelOffset] = cell;
			}
		}
	}

	private static byte[] mergeGrayScale(int[] scanLayer, int width, int height) {
		byte[] completedImage = new byte[width * height * 4];
		for (int i = 0; i < scanLayer.length; i++) {
			for (int j = 0; j < 3; j++) {
				completedImage[i * 4 + j] = clampToByte(scanLayer[i] + 128);
			}
			completedImage[i * 4 + 3] = (byte) 255;
		}

		return completedImage;
	}

	private static byte[] mergeColor(int[][] scanLayer, int width, int height) {
		byte[] completedImage = new byte[width * height * 4];
		for (int i = 0; i < scanLayer[0].length; i++) {
			int Y = scanLayer[0][i] + 128;
			int Cb = scanLayer[1][i] + 128;
			int Cr = scanLayer[2][i] + 128;
			int pixelOffset = i * 4;
			completedImage[pixelOffset] = clampToByte(Y + 1.402 * (Cr - 128));
			completedImage[pixelOffset + 1] = clampToByte(Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
			completedImage[pixelOffset + 2] = clampToByte(Y + 1.772 * (Cb - 128));
			completedImage[pixelOffset + 3] = (byte) 255;
		}

		return completedImage;
	}

	private static byte clampToByte(double value) {
		return (byte) Math.max(0, Math.min(255, value));
	}

}
