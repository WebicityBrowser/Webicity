package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.fetch.FetchDecoderRegistry;
import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchProtocolRegistry;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.connection.imp.HTTPFetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.fetch.imp.FetchNetworkError;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;
import com.github.webicitybrowser.webicity.core.image.imp.ImageLoaderRegistryImp;
import com.github.webicitybrowser.webicity.core.imp.RendererHandleImp;
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
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventLoopImp;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventScheduler;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventSchedulerImp;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.TaskQueueTaskDestination;

public class RenderingEngineImp implements RenderingEngine {

	private final AssetLoader assetLoader;
	private final FetchEngine fetchEngine;
	
	private final ProtocolRegistry protocolRegistry = new ProtocolRegistryImp();
	private final ImageCodecRegistry imageLoaderRegistry = new ImageLoaderRegistryImp();
	private final RendererBackendRegistry rendererBackendRegistry = new RendererBackendRegistryImp();
	private final List<SoftReference<Frame>> frames = new ArrayList<>();

	private final EventLoop internalEventLoop = new EventLoopImp();
	private final EventScheduler internalEventScheduler = new EventSchedulerImp(internalEventLoop);

	public RenderingEngineImp(AssetLoader assetLoader, HTTPService httpService) {
		this.assetLoader = assetLoader;
		
		FetchProtocolRegistry fetchProtocolRegistry = new FetchProtocolRegistryImp(protocolRegistry);
		this.fetchEngine = new FetchEngineImp(
			new HTTPFetchConnectionPool(httpService), fetchProtocolRegistry,
			FetchDecoderRegistry.createDefault(), new ParallelContextImp());
	}

	@Override
	public Frame createFrame() {
		Frame frame = new FrameImp(this);
		frames.add(new SoftReference<>(frame));

		return frame;
	}
	
	@Override
	public void openRenderer(URL url, Frame frame, Consumer<RendererHandle> onRendererOpened) {
		try {
			TaskQueue taskQueue = internalEventLoop.getTaskQueue(EventLoop.NETWORK_TASK_QUEUE);
			FetchParametersBuilder builder = FetchParametersBuilder.create();
			builder.setRequest(FetchRequest.createRequest("GET", url));
			builder.setProcessResponseAction(response ->
				onRendererOpened.accept(maybeOpenRenderer(response)));
			builder.setTaskDestination(new TaskQueueTaskDestination(taskQueue));
			fetchEngine.fetch(builder.build());
		} catch (Exception e) {
			if (e instanceof RendererCrashException crashException) {
				onRendererOpened.accept(RendererHandleImp.fail(crashException.getReason()));
			} else {
				onRendererOpened.accept(RendererHandleImp.fail(new ExceptionRendererCrashReason(e)));
			}
		}
	}

	@Override
	public RendererHandle createBlankRenderer() {
		return RendererHandleImp.fail(new GenericRendererCrashReason("NO_RENDERER"));
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

		internalEventScheduler.tick();
	}

	private RendererHandle maybeOpenRenderer(FetchResponse response) {
		if (response instanceof FetchNetworkError) {
			return RendererHandleImp.fail(new GenericRendererCrashReason("NETWORK_FAILURE"));
		}
		
		return openRenderer(response);
	}
	
	private RendererHandle openRenderer(FetchResponse response) {
		String contentType = response.headerList().getHeaderValue("Content-Type");
		if (contentType == null) {
			contentType = "text/html"; // TODO: Correctly fallback
		}
		contentType = contentType.split(";")[0];

		return rendererBackendRegistry
			.getBackendFactory(contentType)
			.map(factory -> instantiateRendererBackend(factory, response))
			.map(renderer -> RendererHandleImp.of(renderer))
			.orElse(RendererHandleImp.fail(
				new GenericRendererCrashReason("RENDERER_BACKEND_NOT_REGISTERED")));
	}
	
	private RendererBackend instantiateRendererBackend(RendererBackendFactory factory, FetchResponse response) {
		try {
			return factory.create(createRendererContext(response.url()), response);
		} catch (Exception e) {
			throw new RendererCrashException(new ExceptionRendererCrashReason(e));
		}
	}

	private RendererContext createRendererContext(URL url) {
		return new RendererContextImp(this, url);
	}
}
