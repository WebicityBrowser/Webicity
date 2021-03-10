package everyos.browser.webicity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import everyos.browser.webicity.concurrency.ThreadQueue;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.protocol.AboutProtocol;
import everyos.browser.webicity.net.protocol.FileProtocol;
import everyos.browser.webicity.net.protocol.HTTPProtocol;
import everyos.browser.webicity.net.protocol.Protocol;
import everyos.browser.webicity.net.request.Request;
import everyos.browser.webicity.net.response.Response;

public abstract class WebicityEngine {
	protected HashMap<String, Protocol> protocols = new HashMap<>();
	protected ArrayList<Object> locks = new ArrayList<>();
	protected ExecutorService threads = Executors.newWorkStealingPool();
			//Executors.newCachedThreadPool();
	protected boolean stopped = false;
	
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
	};
	
	public ThreadQueue createThreadQueue() {
		ThreadQueue tasks = new ThreadQueue(this);
		threads.execute(()->{
			if (this.stopped) return;
			tasks.run();
		});
		
		return tasks;
	}
	
	public void quit() {
		// TODO: This does not seem to work if you close the program while it is processing
		threads.shutdown();
		locks.forEach(l->{
			synchronized(l) { l.notifyAll(); }	
		});
		locks.clear();
		stopped = true;
	}
	public boolean hasQuit() {
		return stopped;
	}

	public void addActive(ArrayList<Runnable> queue) {
		locks.add(queue);
	}
	public void removeActive(ArrayList<Runnable> queue) {
		locks.remove(queue);
	}
}
