package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.runners.NetworkTaskRunner;

public class EventSchedulerImp implements EventScheduler {

	private final TaskRunner networkTaskRunner;

	public EventSchedulerImp(EventLoop eventLoop) {
		this.networkTaskRunner = new NetworkTaskRunner(
			eventLoop.getTaskQueue(EventLoop.NETWORK_TASK_QUEUE));
	}

	@Override
	public void tick() {
		networkTaskRunner.tick();
	}
	
}
