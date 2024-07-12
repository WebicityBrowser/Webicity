package com.github.webicitybrowser.webicity.renderer.backend.html.tasks.runners;

import java.util.Optional;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.TaskRunner;

public class NetworkTaskRunner implements TaskRunner {

	private final static long MIN_DELAY = 8;
	
	private final TaskQueue taskQueue;

	private long lastTime = 0;

	public NetworkTaskRunner(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	@Override
	public void tick() {
		if (System.currentTimeMillis() - lastTime < MIN_DELAY) return;
		Optional<Runnable> task = taskQueue.poll();
		if (task.isPresent()) {
			task.get().run();
			lastTime = System.currentTimeMillis();
		}
	}
	
}
