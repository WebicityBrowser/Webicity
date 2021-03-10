package everyos.browser.webicity.concurrency;

import java.util.ArrayList;

import everyos.browser.webicity.WebicityEngine;

public class ThreadQueue implements Runnable {
	private boolean stopped;
	private WebicityEngine engine;
	private ArrayList<Runnable> queue = new ArrayList<>();
	
	public ThreadQueue(WebicityEngine engine) {
		this.engine = engine;
	}
	
	@Override public void run() {
		engine.addActive(queue);
		while (!this.hasQuit()) {
			try {
				synchronized(queue) {
					if (queue.isEmpty()) queue.wait();
				}
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
		synchronized(queue) {
			queue.add(action);
			queue.notify();
		}
	}
}
