package everyos.browser.webicity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.protocol.Protocol;
import everyos.browser.webicity.net.protocol.http.HTTPRequest;
import everyos.browser.webicity.net.protocol.io.AboutRequest;
import everyos.browser.webicity.net.protocol.io.FileRequest;
import everyos.browser.webicity.net.protocol.io.WebicityRequest;

public abstract class WebicityEngine {
	private final HashMap<String, Protocol> protocols;
	private final ArrayList<ExecutorService> threads;
	
	private boolean stopped = false;
	
	public WebicityEngine() {
		this.protocols = new HashMap<>();
		this.threads = new ArrayList<>();
	}
	
	public abstract Response processRequest(Request request) throws IOException;
	
	public Request getDefaultRequest(URL url) {
		String protocol = url.getProtocol();
		if (protocols.containsKey(protocol)) {
			return protocols.get(protocol).getGenericRequest(url);
		}
		return null;
	}

	public void registerDefaultProtocols() {
		Protocol httpProtocol = HTTPRequest::new;
		
		registerProtocol("http", httpProtocol);
		registerProtocol("https", httpProtocol);
		
		registerProtocol("about", AboutRequest::new);
		registerProtocol("file", FileRequest::new);
		
		registerProtocol("webicity", WebicityRequest::new);
	}

	public void registerProtocol(String string, Protocol protocol) {
		protocols.put(string, protocol);
	}
	
	public ExecutorService createThreadQueue() {
		ExecutorService thread = Executors.newSingleThreadExecutor();
		threads.add(thread);
		
		return thread;
	}
	
	public void quit() {
		// TODO: This does not work until all running tasks have stopped
		// Should a timeout be added?
		for (ExecutorService thread: threads) {
			thread.shutdown();
		}
		stopped = true;
	}
	
	public boolean hasQuit() {
		return stopped;
	}
}
