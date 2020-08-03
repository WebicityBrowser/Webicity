package everyos.browser.webicity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import everyos.browser.webicity.concurrency.ThreadQueue;
import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.handlers.NavigateHandler;
import everyos.browser.webicity.net.NullStreamHandler;
import everyos.browser.webicity.net.response.Response;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.FontSizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

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

	public WebicityFrame navigate(String urls) {
		try {
			url = new URL(url, urls, NullStreamHandler.instance);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (navigator==null||engine==null) return this;
		
		//TODO: WhatWG specifies how navigation should be done
		navigator.navigate(new NavigateEvent(url));
		
		if (this.innerFrame!=null) innerFrame.delete();
		innerFrame = new BlockComponent(this) //TODO: This should be a Webicity IsolatedComponent
			.directive(SizeDirective.of(new Location(1, 0, 1, 0)))
			.directive(PositionDirective.of(new Location(0, 0, 0, 0)))
			.directive(FontSizeDirective.of(12))
			.directive(BackgroundDirective.of(Color.WHITE));
		
		
		tasks.queue(()->{
			try {
				Response response = engine.getDefaultRequest(url).send();
				response.getProbableRenderer().execute(this, response.getConnection());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		return this;
	}
}
