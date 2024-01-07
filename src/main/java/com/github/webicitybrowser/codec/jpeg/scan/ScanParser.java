package com.github.webicitybrowser.codec.jpeg.scan;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.BitStream;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.ByteArray;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.IntArray;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class ScanParser {
	
	private ScanParser() {}

	public static ScanComponentResult[] read(PushbackInputStream inputStream, ScanComponent[] scanComponents) throws IOException, MalformedJPEGException {
		ByteArray inputBytes = new ByteArray();
		IntArray[] scanComponentOut = createScanComponentOut(scanComponents);
		
		ScanComponent[] scanComponentsOrder = orderScanComponents(scanComponents);
		IntArray[] scanComponentOutOrder = orderScanComponentOut(scanComponents, scanComponentOut);

		while(true) {
			int nextByte = JPEGUtil.read(inputStream);
			int byteAfter = nextByte == 0xFF ? JPEGUtil.read(inputStream) : 0;
			if (byteAfter >= 0xD0 && byteAfter <= 0xD7) {
				addBlocks(inputBytes, scanComponentsOrder, scanComponentOutOrder);
				for (ScanComponent scanComponent: scanComponents) {
					scanComponent.entropyDecoder().restart();
				}
				continue;
			} else if (byteAfter != 0x00) {
				inputStream.unread(byteAfter);
				inputStream.unread(0xFF);
				break;
			}

			inputBytes.add((byte) nextByte);
		}

		addBlocks(inputBytes, scanComponentsOrder, scanComponentOutOrder);

		return createScanComponentResults(scanComponents, scanComponentOut);
	}

	private static void addBlocks(ByteArray inputBytes, ScanComponent[] scanComponentsOrder, IntArray[] scanComponentOutOrder) {
		BitStream bitStream = new BitStream(inputBytes.toArray());
		inputBytes.clear();
		int i = 0;
		while (!onlyOnes(bitStream)) {
			EntropyDecoder entropyDecoder = scanComponentsOrder[i].entropyDecoder();
			int[] result = entropyDecoder.readBlock(bitStream);
			scanComponentOutOrder[i].add(result);
			i = (i + 1) % scanComponentsOrder.length;
		}
	}

	private static boolean onlyOnes(BitStream bitStream) {
		for (int i = 0; i < bitStream.remaining(); i++) {
			if (bitStream.peek(i) != 1) return false;
		}

		return true;
	}

	private static ScanComponent[] orderScanComponents(ScanComponent[] scanComponents) {
		int totalItems = 0;
		for (ScanComponent scanComponent: scanComponents) {
			totalItems += scanComponent.hSample() * scanComponent.vSample();
		}

		ScanComponent[] scanComponentsOrder = new ScanComponent[totalItems];
		int index = 0;
		for (ScanComponent scanComponent: scanComponents) {
			for (int i = 0; i < scanComponent.hSample() * scanComponent.vSample(); i++) {
				scanComponentsOrder[index++] = scanComponent;
			}
		}

		return scanComponentsOrder;
	}

	private static IntArray[] createScanComponentOut(ScanComponent[] scanComponents) {
		IntArray[] scanComponentOut = new IntArray[scanComponents.length];
		for (int i = 0; i < scanComponents.length; i++) {
			scanComponentOut[i] = new IntArray();
		}

		return scanComponentOut;
	}

	private static IntArray[] orderScanComponentOut(ScanComponent[] scanComponents, IntArray[] scanComponentOut) {
		int totalItems = 0;
		for (ScanComponent scanComponent: scanComponents) {
			totalItems += scanComponent.hSample() * scanComponent.vSample();
		}

		IntArray[] scanComponentOutOrder = new IntArray[totalItems];
		int index = 0;
		int j = 0;
		for (ScanComponent scanComponent: scanComponents) {
			IntArray outArray = scanComponentOut[j++];
			for (int i = 0; i < scanComponent.hSample() * scanComponent.vSample(); i++) {
				scanComponentOutOrder[index++] = outArray;
			}
		}

		return scanComponentOutOrder;
	}

	private static ScanComponentResult[] createScanComponentResults(ScanComponent[] scanComponents, IntArray[] scanComponentOut) {
		ScanComponentResult[] scanComponentResults = new ScanComponentResult[scanComponents.length];
		for (int i = 0; i < scanComponents.length; i++) {
			scanComponentResults[i] = new ScanComponentResult(scanComponents[i], scanComponentOut[i].toArray());
		}

		return scanComponentResults;
	}

}
