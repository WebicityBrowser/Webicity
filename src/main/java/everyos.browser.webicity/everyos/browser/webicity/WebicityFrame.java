package everyos.browser.webicity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;

import everyos.browser.spec.jhtml.browsing.BrowsingContext;
import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.concurrency.jroutine.JRoutine;
import everyos.browser.webicity.concurrency.jroutine.JRoutineHelper;
import everyos.browser.webicity.event.FrameCallback;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.renderer.Renderer;

public class WebicityFrame {
	
	//TODO: Move some of this to a "browsing context"
	private final URL url;
	private final FrameCallback callback;
	private final WebicityEngine engine;
	private final ExecutorService tasks;
	@SuppressWarnings("unused")
	private final BrowsingContext context;
	
	private Renderer renderer;

	public WebicityFrame(WebicityEngine engine, FrameCallback callback, URL url, ExecutorService queue) {
		this.engine = engine;
		this.callback = callback;
		this.tasks = queue;
		
		this.context = new BrowsingContext(null, null, null);
		
		this.url = url;
		navigate(url);
	}
	
	public URL getURL() {
		return url;
	}

	private void navigate(URL url) {
		//TODO: WhatWG specifies how navigation should be done
		tasks.execute(()->{
			JRoutineHelper.finish(JRoutine.create(()->{
				try {
					Response response = engine.getDefaultRequest(url).send();
					Renderer renderer = response.getProbableRenderer();
					this.renderer = renderer;
					callback.onRendererCreated(renderer);
					renderer.execute(this, response.getConnection());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}));
		});
	}
	
	public WebicityEngine getEngine() {
		return engine;
	}
	
	public ExecutorService getTasks() {
		return tasks;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public String getTitle() {
		if (renderer!=null) {
			return renderer.getTitle();
		}
		return null;
	}
	
	public void quit() {
		tasks.shutdown();
	}

	//TODO: Specify target
	public void browse(String href) {
		try {
			URL url = new URL(this.url, href);
			callback.onNavigate(()->url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}