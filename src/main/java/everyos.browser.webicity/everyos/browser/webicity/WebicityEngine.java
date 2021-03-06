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
import everyos.browser.webicity.net.protocol.http.HTTPProtocol;
import everyos.browser.webicity.net.protocol.io.AboutProtocol;
import everyos.browser.webicity.net.protocol.io.FileProtocol;

public abstract class WebicityEngine {
	private HashMap<String, Protocol> protocols = new HashMap<>();
	private ArrayList<ExecutorService> threads = new ArrayList<>();
	private boolean stopped = false;
	
	public abstract Response processRequest(Request request) throws IOException;
	
	public Request getDefaultRequest(URL url) {
		String protocol = url.getProtocol();
		if (protocols.containsKey(protocol)) {
			return protocols.get(protocol).getGenericRequest(url);
		}
		return null;
	}

	public void registerDefaultProtocols() {
		Protocol httpProtocol = new HTTPProtocol();
		registerProtocol("http", httpProtocol);
		registerProtocol("https", httpProtocol);
		
		registerProtocol("about", new AboutProtocol());
		registerProtocol("file", new FileProtocol());
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
