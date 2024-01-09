package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkInfo;

public record IDATContext(IHDRChunkInfo ihdrChunkInfo, PLTEChunkInfo plteChunkInfo) {
	
}
