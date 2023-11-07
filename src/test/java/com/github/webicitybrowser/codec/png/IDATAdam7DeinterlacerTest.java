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

		Assertions.assertEquals(new byte[] {
			1, 6, 4, 6, 2, 6, 4, 6,
			7, 7, 7, 7, 5, 7, 7, 7,
			5, 6, 5, 6, 5, 6, 5, 6,
			7, 7, 7, 7, 7, 7, 7, 7,
			3, 6, 4, 6, 3, 6, 4, 6,
			7, 7, 7, 7, 7, 7, 7, 7,
			5, 6, 5, 6, 5, 6, 5, 6,
			7, 7, 7, 7, 7, 7, 7, 7,
		}, deinterlacedImage);
	}

	private byte[] removeScanlines(byte[] b, int w, int h) {
		byte[] result = new byte[b.length - h];
		int resultIndex = 0;
		for (int i = 0; i < b.length; i++) {
			if (i % (w + 1) != 0) result[resultIndex++] = b[i];
		}

		return result;
	}

}
