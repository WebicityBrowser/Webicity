package com.github.webicitybrowser.spec.fetch.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.webicitybrowser.spec.fetch.FetchDecoder;
import com.github.webicitybrowser.spec.fetch.FetchDecoderRegistry;

public class FetchDecoderRegistryImp implements FetchDecoderRegistry {

	private final Map<String, Supplier<FetchDecoder>> decoders = new HashMap<>();

	@Override
	public void registerDecoder(String name, Supplier<FetchDecoder> decoderSupplier) {
		decoders.put(name, decoderSupplier);
	}

	@Override
	public FetchDecoder getDecoder(String encoding) {
		Supplier<FetchDecoder> decoderSupplier = decoders.get(encoding);
		if(decoderSupplier == null) return null;
		return decoderSupplier.get();
	}
	
}
