package com.github.webicitybrowser.spec.fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.webicitybrowser.spec.fetch.decoder.DeflateDecoder;
import com.github.webicitybrowser.spec.fetch.decoder.GzipDecoder;
import com.github.webicitybrowser.spec.fetch.imp.FetchDecoderRegistryImp;

public interface FetchDecoderRegistry {

	void registerDecoder(String name, Supplier<FetchDecoder> decoderSupplier);

	FetchDecoder getDecoder(String encoding);
	
	default List<FetchDecoder> getDecoders(FetchHeaderList headerList) {
		List<String> contentEncodings = FetchHeaderLogic.extractHeaderListValues("Content-Encoding", headerList);
		if(contentEncodings == null) return List.of();
		
		List<FetchDecoder> decoders = new ArrayList<>();
		for(String encoding : contentEncodings) {
			FetchDecoder decoder = getDecoder(encoding);
			if(decoder != null) {
				decoders.add(decoder);
			}
		}

		return decoders;
	}

	static FetchDecoderRegistry create() {
		return new FetchDecoderRegistryImp();
	}

	static FetchDecoderRegistry createDefault() {
		FetchDecoderRegistry registry = create();
		registry.registerDecoder("gzip", GzipDecoder::new);
		registry.registerDecoder("deflate", DeflateDecoder::new);

		return registry;
	}
	
}
