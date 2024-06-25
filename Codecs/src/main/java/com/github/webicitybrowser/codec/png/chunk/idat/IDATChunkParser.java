package com.github.webicitybrowser.codec.png.chunk.idat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public final class IDATChunkParser {
	
	private IDATChunkParser() {}

	public static IDATChunkInfo parse(byte[] data, IDATContext idatContext) throws IOException, UnsupportedPNGException {
		byte[] decompressedData = decompress(data, idatContext);
		byte[] imageRaster = deinterlaceAndDecode(decompressedData, idatContext);
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

	private static byte[] deinterlaceAndDecode(byte[] unfilteredData, IDATContext idatContext) throws UnsupportedPNGException {
		IDATImageRasterDecoder imageRasterDecoder = (b, w, h) -> createImageRaster(unfilter(b, idatContext, w, h), idatContext);
		IHDRChunkInfo headerInfo = idatContext.ihdrChunkInfo();

		return switch (idatContext.ihdrChunkInfo().interlaceMethod()) {
		case 0 -> imageRasterDecoder.decode(unfilteredData, headerInfo.width(), headerInfo.height());
		case 1 -> IDATAdam7Deinterlacer.deinterlace(unfilteredData, headerInfo, imageRasterDecoder);
		default -> throw new UnsupportedPNGException();
		};
	}

	private static byte[] unfilter(byte[] decompressedData, IDATContext idatContext, int width, int height) throws UnsupportedPNGException {
		return switch (idatContext.ihdrChunkInfo().filterMethod()) {
		case 0 -> IDATUnfilter.unfilter(decompressedData, idatContext.ihdrChunkInfo(), width, height);
		default -> throw new UnsupportedPNGException();
		};
	}

	private static byte[] createImageRaster(byte[] unfilteredData, IDATContext idatContext) throws UnsupportedPNGException {
		return switch (idatContext.ihdrChunkInfo().colorType()) {
		case 2 -> IDATColorDecoder.decodeTrueColor(unfilteredData, idatContext);
		case 3 -> IDATColorDecoder.decodeIndexedColor(unfilteredData, idatContext);
		case 6 -> IDATColorDecoder.decodeTrueColorWithAlpha(unfilteredData, idatContext);
		default -> throw new UnsupportedPNGException();
		};
	}

}
