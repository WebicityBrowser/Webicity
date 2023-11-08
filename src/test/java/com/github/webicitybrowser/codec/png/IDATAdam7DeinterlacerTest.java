package com.github.webicitybrowser.codec.png;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.codec.png.chunk.idat.IDATAdam7Deinterlacer;
import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;

public class IDATAdam7DeinterlacerTest {
	
	@Test
	@DisplayName("Can deinterlace image")
	public void canDeinterlaceImage() {
		byte[] interlacedImage = new byte[] {
			0, 1,
			0, 2,
			0, 3, 3,
			0, 4, 4,
			0, 4, 4,
			0, 5, 5, 5, 5,
			0, 5, 5, 5, 5,
			0, 6, 6, 6, 6,
			0, 6, 6, 6, 6,
			0, 6, 6, 6, 6,
			0, 6, 6, 6, 6,
			0, 7, 7, 7, 7, 7, 7, 7, 7,
			0, 7, 7, 7, 7, 7, 7, 7, 7,
			0, 7, 7, 7, 7, 7, 7, 7, 7,
			0, 7, 7, 7, 7, 7, 7, 7, 7,
		};

		IHDRChunkInfo ihdrChunkInfo = new IHDRChunkInfo(8, 8, (byte) 8, (byte) 0, (byte) 0, (byte) 0, (byte) 1);

		byte[] deinterlacedImage = Assertions.assertDoesNotThrow(
			() -> IDATAdam7Deinterlacer.deinterlace(interlacedImage, ihdrChunkInfo, (b, w, h) -> removeScanlines(b, w, h))
		);

		Assertions.assertArrayEquals(new byte[] {
			1, 0, 0, 0, 6, 0, 0, 0, 4, 0, 0, 0, 6, 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 4, 0, 0, 0, 6, 0, 0, 0,
			7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0,
			5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0,
			7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0,
			3, 0, 0, 0, 6, 0, 0, 0, 4, 0, 0, 0, 6, 0, 0, 0, 3, 0, 0, 0, 6, 0, 0, 0, 4, 0, 0, 0, 6, 0, 0, 0,
			7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0,
			5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0,
			7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0, 7, 0, 0, 0,
		}, deinterlacedImage);
	}

	private byte[] removeScanlines(byte[] b, int w, int h) {
		byte[] result = new byte[(b.length - h) * 4];
		int resultIndex = 0;
		for (int i = 0; i < b.length; i++) {
			if (i % (w + 1) != 0) result[resultIndex++ * 4] = b[i];
		}

		return result;
	}

}
