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

	public static int[] read(PushbackInputStream inputStream, ScanComponent[] scanComponents) throws IOException, MalformedJPEGException {
		ByteArray inputBytes = new ByteArray();
		IntArray outputInts = new IntArray();
		ScanComponent[] scanComponentsOrder = orderScanComponents(scanComponents);

		while(true) {
			int nextByte = JPEGUtil.read(inputStream);
			int byteAfter = nextByte == 0xFF ? JPEGUtil.read(inputStream) : 0;
			if (byteAfter >= 0xD0 && byteAfter <= 0xD7) {
				addBlocks(inputBytes, outputInts, scanComponentsOrder);
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

		addBlocks(inputBytes, outputInts, scanComponentsOrder);

		return outputInts.toArray();
	}

	private static void addBlocks(ByteArray inputBytes, IntArray outputInts, ScanComponent[] scanComponentsOrder) {
		BitStream bitStream = new BitStream(inputBytes.toArray());
		inputBytes.clear();
		int i = 0;
		while (!onlyOnes(bitStream)) {
			EntropyDecoder entropyDecoder = scanComponentsOrder[i].entropyDecoder();
			int[] result = entropyDecoder.readBlock(bitStream);
			if (scanComponentsOrder[i].componentId() == 1) outputInts.add(result);
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

}
