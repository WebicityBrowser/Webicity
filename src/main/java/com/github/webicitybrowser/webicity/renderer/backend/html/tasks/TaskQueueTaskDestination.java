package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;

public class TaskQueueTaskDestination implements TaskDestination {
	
	private final TaskQueue taskQueue;

	public TaskQueueTaskDestination(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	@Override
	public void enqueue(Runnable task) {
		taskQueue.enqueue(task);
	}

}
