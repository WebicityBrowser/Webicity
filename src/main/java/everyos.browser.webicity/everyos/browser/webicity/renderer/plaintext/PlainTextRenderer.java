package everyos.browser.webicity.renderer.plaintext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;

public class PlainTextRenderer implements Renderer {
	private final List<Runnable> readyHooks;
	
	private String content;

	public PlainTextRenderer() {
		this.readyHooks = new ArrayList<>();
	}
	
	//TODO: WhatWG actually specifies how this should be done
	@Override 
	public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		
		//TODO: Specialized rendering context, to prevent lag on browser UI
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder builder = new StringBuilder();
		
		while (!frame.getTasks().isShutdown()) {
			final int read = reader.read();
			if (read==-1) break;
			
			builder.append(read);
		}
		stream.close();
		
		this.content = builder.toString();
		
		for (Runnable hook: readyHooks) {
			hook.run();
		}
		readyHooks.clear();
	}

	@Override
	public String getTitle() {
		return "Text Reader";
	}

	@Override
	public void addReadyHook(Runnable hook) {
		if (this.content != null) {
			hook.run();
		} else {
			readyHooks.add(hook);
		}
	}
	
	public String getContent() {
		return content;
	}
}
