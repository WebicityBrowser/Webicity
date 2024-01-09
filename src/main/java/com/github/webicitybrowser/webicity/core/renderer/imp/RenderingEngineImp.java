package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchProtocolRegistry;
import com.github.webicitybrowser.spec.fetch.connection.imp.HTTPFetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;
import com.github.webicitybrowser.webicity.core.image.imp.ImageLoaderRegistryImp;
import com.github.webicitybrowser.webicity.core.imp.RendererHandleImp;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.core.net.imp.ProtocolRegistryImp;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.GenericRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackend;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackendFactory;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackendRegistry;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashException;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.ui.Frame;
import com.github.webicitybrowser.webicity.core.ui.imp.FrameImp;

public class RenderingEngineImp implements RenderingEngine {

	private final AssetLoader assetLoader;
	private final FetchEngine fetchEngine;
	
	private final ProtocolRegistry protocolRegistry = new ProtocolRegistryImp();
	private final ImageCodecRegistry imageLoaderRegistry = new ImageLoaderRegistryImp();
	private final RendererBackendRegistry rendererBackendRegistry = new RendererBackendRegistryImp();
	private final List<SoftReference<Frame>> frames = new ArrayList<>();

	public RenderingEngineImp(AssetLoader assetLoader, HTTPService httpService) {
		this.assetLoader = assetLoader;
		
		FetchProtocolRegistry fetchProtocolRegistry = new FetchProtocolRegistryImp(protocolRegistry);
		this.fetchEngine = new FetchEngineImp(new HTTPFetchConnectionPool(httpService), fetchProtocolRegistry, new ParallelContextImp());
	}

	@Override
	public Frame createFrame() {
		Frame frame = new FrameImp(this);
		frames.add(new SoftReference<>(frame));

		return frame;
	}
	
	@Override
	public RendererHandle openRenderer(URL url, Frame frame) {
		Optional<Protocol> protocol = protocolRegistry.getProtocolForURL(url);
		if (protocol.isEmpty()) {
			return RendererHandleImp.fail(new GenericRendererCrashReason("PROTOCOL_NOT_REGISTERED"));
		}
		
		try {
			ProtocolContext context = new ProtocolContext("GET", redirectURL -> frame.redirect(redirectURL));
			Connection connection = protocol.get().openConnection(url, context);
			return openRenderer(connection);
		} catch (Exception e) {
			if (e instanceof RendererCrashException crashException) {
				return RendererHandleImp.fail(crashException.getReason());
			}
			return RendererHandleImp.fail(new ExceptionRendererCrashReason(e));
		}
	}

	@Override
	public AssetLoader getAssetLoader() {
		return this.assetLoader;
	}

	@Override
	public FetchEngine getFetchEngine() {
		return fetchEngine;
	}

	@Override
	public HTTPService getHTTPService() {
		return null;
	}

	@Override
	public ProtocolRegistry getProtocolRegistry() {
		return this.protocolRegistry;
	}

	@Override
	public ImageCodecRegistry getImageLoaderRegistry() {
		return this.imageLoaderRegistry;
	}

	@Override
	public RendererBackendRegistry getBackendRendererRegistry() {
		return this.rendererBackendRegistry;
	}

	@Override
	public void tick() {
		for (int i = 0; i < frames.size(); i++) {
			SoftReference<Frame> frameReference = frames.get(i);
			Frame frame = frameReference.get();
			if (frame == null) {
				frames.remove(i);
				i--;
				continue;
			}
			
			frame.tick();
		}
	}
	
	private RendererHandle openRenderer(Connection connection) {
		return rendererBackendRegistry
			.getBackendFactory(connection.getContentType())
			.map(factory -> instantiateRendererBackend(factory, connection))
			.map(renderer -> RendererHandleImp.of(renderer))
			.orElse(RendererHandleImp.fail(
				new GenericRendererCrashReason("RENDERER_BACKEND_NOT_REGISTERED")));
	}
	
	private RendererBackend instantiateRendererBackend(RendererBackendFactory factory, Connection connection) {
		try {
			return factory.create(createRendererContext(connection.getURL()), connection);
		} catch (Exception e) {
			throw new RendererCrashException(new ExceptionRendererCrashReason(e));
		}
	}

	private RendererContext createRendererContext(URL url) {
		return new RendererContextImp(this, url);
	}
}
