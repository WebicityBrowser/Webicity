package everyos.browser.webicity;

import java.io.IOException;
import java.net.MalformedURLException;

import everyos.browser.webicity.concurrency.ThreadQueue;
import everyos.browser.webicity.event.FrameCallback;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.renderer.Renderer;

public class WebicityFrame {
	private final URL url;
	private final FrameCallback callback;
	private final WebicityEngine engine;
	private ThreadQueue tasks;
	private Renderer renderer;

	public WebicityFrame(WebicityEngine engine, FrameCallback callback, URL url, ThreadQueue queue) {
		this.engine = engine;
		this.callback = callback;
		this.tasks = queue;
		
		this.url = url;
		navigate(url);
	}
	
	public URL getURL() {
		return url;
	}

	private void navigate(URL url) {
		//TODO: WhatWG specifies how navigation should be done
		tasks.queue(()->{
			try {
				Response response = engine.getDefaultRequest(url).send();
				// Note that we are on the wrong thread here, but that is actually a good thing, I think
				Renderer renderer = response.getProbableRenderer();
				this.renderer = renderer;
				callback.onRendererCreated(renderer);
				renderer.execute(this, response.getConnection());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public WebicityEngine getEngine() {
		return engine;
	}
	
	public ThreadQueue getTasks() {
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

	//TODO: Specify target
	public void browse(String href) {
		try {
			callback.onNavigate(new NavigateEvent(new URL(url, href)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}