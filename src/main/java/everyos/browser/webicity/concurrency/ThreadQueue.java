package everyos.browser.webicity.concurrency;

import java.util.ArrayList;

import everyos.browser.webicity.WebicityEngine;

public class ThreadQueue implements Runnable{
	protected boolean stopped;
	protected WebicityEngine engine;
	protected ArrayList<Runnable> queue = new ArrayList<>();
	
	public ThreadQueue(WebicityEngine engine) {
		this.engine = engine;
	}
	
	@Override public void run() {
		engine.addActive(queue);
		while (!this.hasQuit()) {
			try {
				synchronized(queue) {queue.wait();}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!queue.isEmpty()) {
				queue.get(0).run();
				queue.remove(0);
			}
		}
		engine.removeActive(queue);
	};
	
	public void quit() {
		this.stopped = true;
		synchronized(queue) {queue.notify();}
	};
	public boolean hasQuit() {
		return stopped||engine.hasQuit();
	}
	public void queue(Runnable action) {
		queue.add(action);
		synchronized(queue) {queue.notify();}
	}
}
