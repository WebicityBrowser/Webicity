package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;

public class EventSchedulerImp implements EventScheduler {

	private static final int ALLOWED_TIME = 8;

	private final EventLoop eventLoop;

	public EventSchedulerImp(EventLoop eventLoop) {
		this.eventLoop = eventLoop;
	}

	@Override
	public void tick() {
		TaskQueue networkTaskQueue = eventLoop.getTaskQueue(EventLoop.NETWORK_TASK_QUEUE);

		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < ALLOWED_TIME) {
			networkTaskQueue.poll().ifPresent(Runnable::run);
		}
	}
	
}
