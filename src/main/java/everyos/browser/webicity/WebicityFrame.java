package everyos.browser.webicity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import everyos.browser.webicity.concurrency.ThreadQueue;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.handlers.NavigateHandler;
import everyos.browser.webicity.net.NullStreamHandler;
import everyos.browser.webicity.net.response.Response;
import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.component.BlockComponent;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.shape.Location;

public class WebicityFrame extends BlockComponent {
	//private Thread thread;
	protected URL url;
	protected NavigateHandler navigator;
	
	public WebicityEngine engine;
	public ThreadQueue tasks;
	public Component innerFrame;

	public WebicityFrame(Component parent, WebicityEngine engine, NavigateHandler navigator) {
		super(parent);
		
		this.engine = engine;
		this.navigator = navigator;
		
		tasks = new ThreadQueue(engine);
		engine.createThread(tasks);
		
		try {
			url = new URL(null, "about:blank", NullStreamHandler.instance);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override public Component attribute(String name, Object attr) {
		super.attribute(name, attr);
		
		if (name=="url") {
			if (attr instanceof URL) {
				url = (URL) attr;
			} else {
				try {
					url = new URL(url, (String) attr, NullStreamHandler.instance);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			if (navigator==null||engine==null) return this;
			
			//TODO: WhatWG specifies how navigation should be done
			navigator.navigate(new NavigateEvent(url));
			
			if (this.innerFrame!=null) innerFrame.delete();
			innerFrame = new BlockComponent(this) //TODO: This should be a Webicity IsolatedComponent
				.attribute("size", new Location(1, 0, 1, 0))
				.attribute("position", new Location(0, 0, 0, 0))
				.attribute("font-size", 12)
				.attribute("bg-color", Color.WHITE);
			
			
			tasks.queue(()->{
				try {
					Response response = engine.getDefaultRequest(url).send();
					response.getProbableRenderer().execute(this, response.getConnection());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		
		return this;
	}
}
