package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
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
	
	private final ProtocolRegistry protocolRegistry = new ProtocolRegistryImp();
	private final RendererBackendRegistry rendererBackendRegistry = new RendererBackendRegistryImp();

	public RenderingEngineImp(AssetLoader assetLoader) {
		this.assetLoader = assetLoader;
	}

	@Override
	public Frame createFrame() {
		return new FrameImp(this);
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
	public ProtocolRegistry getProtocolRegistry() {
		return this.protocolRegistry;
	}

	@Override
	public RendererBackendRegistry getBackendRendererRegistry() {
		return this.rendererBackendRegistry;
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
			return factory.create(createRendererContext(), connection);
		} catch (Exception e) {
			throw new RendererCrashException(new ExceptionRendererCrashReason(e));
		}
	}

	private RendererContext createRendererContext() {
		return new RendererContextImp(assetLoader);
	}
}
