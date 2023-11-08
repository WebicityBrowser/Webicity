package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public class IDATAdam7Deinterlacer {

	private static final int[] PASS_WIDTH_INFO = new int[] { 0, 8, 4, 8, 0, 4, 2, 4, 0, 2, 1, 2, 0, 1 };
	private static final int[] PASS_HEIGHT_INFO = new int[] { 0, 8, 0, 8, 4, 8, 0, 4, 2, 4, 0, 2, 1, 2 };

	public static byte[] deinterlace(
		byte[] unfilteredData, IHDRChunkInfo headerInfo, IDATImageRasterDecoder imageRasterDecoder
	) throws UnsupportedPNGException {
		int j = 0;
		boolean isHorizontal = true;
		byte[] deinterlacedData = new byte[0];
		for (int i = 0; i < 7; i++) {
			int passWidth = computePassWidth(headerInfo, i);
			int passHeight = computePassHeight(headerInfo, i);
			if (passWidth == 0 || passHeight == 0) continue;
			int passBytes = pixelLengthToByteLength(headerInfo, passWidth * passHeight) + passHeight;
			byte[] passData = isolatePassData(unfilteredData, j, passBytes);
			byte[] decodedPass = imageRasterDecoder.decode(passData, passWidth, passHeight);
			deinterlacedData = arrangeInterlacedData(deinterlacedData, decodedPass, headerInfo, isHorizontal, passWidth, passHeight);
			j += passBytes;
			isHorizontal = !isHorizontal;
		}

		return deinterlacedData;
	}

	private static int pixelLengthToByteLength(IHDRChunkInfo headerInfo, int pixelLength) {
		return (int) Math.ceil(pixelLength * IDATUtil.getColorBytes(headerInfo));
	}

	private static int computePassWidth(IHDRChunkInfo headerInfo, int passNum) {
		int width = headerInfo.width();
		int offset = PASS_WIDTH_INFO[passNum * 2];
		int step = PASS_WIDTH_INFO[passNum * 2 + 1];
		return (width - offset + step - 1) / step;
	}

	private static int computePassHeight(IHDRChunkInfo headerInfo, int passNum) {
		int height = headerInfo.height();
		int offset = PASS_HEIGHT_INFO[passNum * 2];
		int step = PASS_HEIGHT_INFO[passNum * 2 + 1];
		return (height - offset + step - 1) / step;
	}

	private static byte[] isolatePassData(byte[] unfilteredData, int offset, int numPassBytes) {
		byte[] passData = new byte[numPassBytes];
		for (int i = 0; i < numPassBytes; i++) {
			passData[i] = unfilteredData[offset + i];
		}

		return passData;
	}

	private static byte[] arrangeInterlacedData(
		byte[] deinterlacedData, byte[] decodedPass, IHDRChunkInfo headerInfo, boolean isHorizontal, int passWidth, int passHeight
	) {
		if (isHorizontal) {
			return arrangeHorizontalInterlacedData(deinterlacedData, decodedPass, headerInfo, passWidth, passHeight);
		} else {
			return arrangeVerticalInterlacedData(deinterlacedData, decodedPass, headerInfo, passWidth, passHeight);
		}
	}

	private static byte[] arrangeVerticalInterlacedData(
		byte[] priorData, byte[] newPass, IHDRChunkInfo headerInfo, int passWidth, int passHeight
	) {
		if (priorData.length == 0) return newPass;
		byte[] decodedPass = new byte[priorData.length + newPass.length];
		for (int i = 0; i < decodedPass.length / 8; i++) {
			// TODO: What about images with width not divisible by 8?
			int j = i * 8;
			for (int k = 0; k < 4; k++) {
				decodedPass[j + k] = priorData[i * 4 + k];
			}
			if (j + 4 >= decodedPass.length) continue;
			for (int k = 0; k < 4; k++) {
				decodedPass[j + k + 4] = newPass[i * 4 + k];
			}
		}

		return decodedPass;
	}

	private static byte[] arrangeHorizontalInterlacedData(
		byte[] priorData, byte[] newPass, IHDRChunkInfo headerInfo, int passWidth, int passHeight
	) {
		if (priorData.length == 0) return newPass;
		byte[] decodedPass = new byte[priorData.length + newPass.length];
		for (int i = 0; i < passHeight; i++) {
			for (int j = 0; j < passWidth * 4; j++) {
				int sourceIndex = i * passWidth * 4 + j;
				int destinationIndexPrior = i * 2 * passWidth * 4 + j;
				int destinationIndexNew = (i * 2 + 1) * passWidth * 4 + j;
				decodedPass[destinationIndexPrior] = priorData[sourceIndex];
				if (destinationIndexNew >= decodedPass.length) continue;
				decodedPass[destinationIndexNew] = newPass[sourceIndex];
			}
		}

		return decodedPass;
	}
	
}
