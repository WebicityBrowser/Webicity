package com.github.webicitybrowser.codec.png.imp.chunk.idat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public final class IDATChunkParser {
	
	private IDATChunkParser() {}

	public static IDATChunkInfo parse(byte[] data, IDATContext idatContext) throws IOException, UnsupportedPNGException {
		byte[] decompressedData = decompress(data, idatContext);
		byte[] unfilteredData = unfilter(decompressedData, idatContext);
		byte[] imageRaster = createImageRaster(unfilteredData, idatContext);
		return new IDATChunkInfo(imageRaster);
	}

	private static byte[] decompress(byte[] data, IDATContext idatContext) throws IOException, UnsupportedPNGException {
		return switch (idatContext.ihdrChunkInfo().compressionMethod()) {
		case 0 -> deflateDecompress(data);
		default -> throw new UnsupportedPNGException();
		};
	}

	private static byte[] deflateDecompress(byte[] data) throws IOException {
		InputStream inputStream = new ByteArrayInputStream(data);
		InputStream decompressedStream = new InflaterInputStream(inputStream);
		return decompressedStream.readAllBytes();
	}

	private static byte[] unfilter(byte[] decompressedData, IDATContext idatContext) throws UnsupportedPNGException {
		return switch (idatContext.ihdrChunkInfo().filterMethod()) {
		case 0 -> decompressedData;
		default -> throw new UnsupportedPNGException();
		};
	}

	private static byte[] createImageRaster(byte[] unfilteredData, IDATContext idatContext) throws UnsupportedPNGException {
		return switch (idatContext.ihdrChunkInfo().colorType()) {
		case 2 -> IDATColorDecoder.decodeTrueColor(unfilteredData, idatContext);
		case 6 -> IDATColorDecoder.decodeTrueColorWithAlpha(unfilteredData, idatContext);
		default -> throw new UnsupportedPNGException();
		};
	}

}
