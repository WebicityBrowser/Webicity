package com.github.webicitybrowser.codec.jpeg.scan;

import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.BitStream;

/**
 * An entropy decoder is a decoder that decodes the entropy encoded data
 * of a single JPEG image component. It converts it into the quantized DCT
 * coefficients of the JPEG image that then can be decoded by further processes.
 */
public interface EntropyDecoder {

	/**
	 * Reads a single block of entropy encoded data from the given bit stream.
	 * @param bitStream the bit stream to read from
	 * @return the decoded (yet still quantized) block
	 */
	int[] readBlock(BitStream bitStream);

	/**
	 * Restarts the entropy decoder. This resets internal state that is
	 * necessary to decode the next block. This method should be called after
	 * each restart interval.
	 */
	void restart();

}
