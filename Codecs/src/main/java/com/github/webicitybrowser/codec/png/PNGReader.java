package com.github.webicitybrowser.codec.png;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.codec.png.chunk.idat.IDATChunkParser;
import com.github.webicitybrowser.codec.png.chunk.idat.IDATContext;
import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkParser;
import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkInfo;
import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkParser;
import com.github.webicitybrowser.codec.png.exception.InvalidPNGSignatureException;
import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;
import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public class PNGReader {
	
	private static final byte[] SIGNATURE = asBytes(new int[] { 137, 'P', 'N', 'G', 13, 10, 26, 10 });

	private PNGState state;
	private IHDRChunkInfo ihdrChunkInfo;
	private PLTEChunkInfo plteChunkInfo;
	private List<byte[]> idatBytes;

	public PNGResult read(InputStream dataStream) throws MalformedPNGException, IOException {
		clearState();

		checkSignature(dataStream);
		while (true) {
			PNGChunkInfo chunkInfo = PNGChunkInfoReader.read(dataStream);
			if (isChunkType(chunkInfo, "IEND")) break;
			handleChunk(chunkInfo);
		}

		if (dataStream.read() != -1) throw new MalformedPNGException();

		return new PNGResult(ihdrChunkInfo.width(), ihdrChunkInfo.height(), createFinalImageRaster());
	}

	private void clearState() {
		this.state = PNGState.HEADER;
		this.ihdrChunkInfo = null;
		this.plteChunkInfo = null;
		this.idatBytes = new ArrayList<>();
	}

	private void checkSignature(InputStream dataStream) throws MalformedPNGException, IOException {
		for (int i = 0; i < SIGNATURE.length; i++) {
			int ch = dataStream.read();
			if (ch < 0 || (byte) ch != SIGNATURE[i]) {
				throw new InvalidPNGSignatureException();
			}
		}
	}

	private static byte[] asBytes(int[] data) {
		byte[] bytes = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			bytes[i] = (byte) data[i];
		}
		return bytes;
	}

	private static boolean isChunkType(PNGChunkInfo chunkInfo, String type) {
		byte[] chunkType = chunkInfo.type();
		for (int i = 0; i < type.length(); i++) {
			if (chunkType[i] != type.charAt(i)) {
				return false;
			}
		}

		return true;
	}

	private void handleChunk(PNGChunkInfo chunkInfo) throws MalformedPNGException, IOException {
		switch (state) {
			case HEADER:
				handleHeaderState(chunkInfo);
				break;
			case BEFORE_PLTE:
				handleBeforePLTEState(chunkInfo);
				break;
			case BEFORE_IDAT:
				handleBeforeIDATState(chunkInfo);
				break;
			case BEFORE_IEND:
				handleBeforeIENDState(chunkInfo);
				break;
		}
	}

	private void handleHeaderState(PNGChunkInfo chunkInfo) throws MalformedPNGException {
		if (isChunkType(chunkInfo, "IHDR")) {
			ihdrChunkInfo = IHDRChunkParser.parse(chunkInfo.data());
			state = PNGState.BEFORE_PLTE;
		} else {
			throw new MalformedPNGException("IHDR chunk must be first chunk");
		}
	}

	private void handleBeforePLTEState(PNGChunkInfo chunkInfo) throws MalformedPNGException, IOException {
		if (isChunkType(chunkInfo, "PLTE")) {
			plteChunkInfo = PLTEChunkParser.parse(chunkInfo.data());
			state = PNGState.BEFORE_IDAT;
		} else if (handleBeforeIDATChunk(chunkInfo)) { 
			return;
		} else if (handleBeforeIENDChunk(chunkInfo)) {
			return;
		} else if (!canSkipChunk(chunkInfo)) {
			throw new MalformedPNGException("Unrecognized critical chunk before PLTE");
		}
	}

	private void handleBeforeIDATState(PNGChunkInfo chunkInfo) throws MalformedPNGException, IOException {
		if (handleBeforeIDATChunk(chunkInfo)) {
			return;
		} else if (handleBeforeIENDChunk(chunkInfo)) {
			return;
		} else if (!canSkipChunk(chunkInfo)) {
			throw new MalformedPNGException("Unrecognized critical chunk after PLTE");
		}
	}

	private void handleBeforeIENDState(PNGChunkInfo chunkInfo) throws MalformedPNGException, IOException {
		if (handleBeforeIENDChunk(chunkInfo)) {
			return;
		} else if (!canSkipChunk(chunkInfo)) {
			throw new MalformedPNGException("Unrecognized critical chunk after PLTE");
		}
	}

	private boolean handleBeforeIDATChunk(PNGChunkInfo chunkInfo) {
		return false;
	}

	private boolean handleBeforeIENDChunk(PNGChunkInfo chunkInfo) throws UnsupportedPNGException, IOException {
		if (isChunkType(chunkInfo, "IDAT")) {
			handleIDATChunk(chunkInfo);
			return true;
		}

		return false;
	}

	private void handleIDATChunk(PNGChunkInfo chunkInfo) throws UnsupportedPNGException, IOException {
		this.state = PNGState.BEFORE_IEND;
		idatBytes.add(chunkInfo.data());
	}

	private boolean canSkipChunk(PNGChunkInfo chunkInfo) {
		return (chunkInfo.type()[0] & 32) != 0;
	}

	private byte[] createFinalImageRaster() throws UnsupportedPNGException, IOException {
		int numTotalBytes = 0;
		for (byte[] idatBytes : idatBytes) {
			numTotalBytes += idatBytes.length;
		}

		byte[] allIDATBytes = new byte[numTotalBytes];
		int index = 0;
		for (byte[] idatBytes : idatBytes) {
			System.arraycopy(idatBytes, 0, allIDATBytes, index, idatBytes.length);
			index += idatBytes.length;
		}

		IDATContext idatContext = new IDATContext(ihdrChunkInfo, plteChunkInfo);

		return IDATChunkParser.parse(allIDATBytes, idatContext).imageRaster();
	}

	private static enum PNGState {
		HEADER, BEFORE_PLTE, BEFORE_IDAT, BEFORE_IEND
	}

}
