package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;

public class EventLoopImp implements EventLoop {

	private final TaskQueue[] taskQueues;

	public EventLoopImp() {
		this.taskQueues = createTaskQueues();
	}

	@Override
	public TaskQueue getTaskQueue(int id) {
		return taskQueues[id];
	}

	private TaskQueue[] createTaskQueues() {
		TaskQueue[] taskQueues = new TaskQueue[TYPES_OF_TASK_QUEUES];
		for(int i = 0; i < taskQueues.length; i++) {
			taskQueues[i] = new TaskQueueImp();
		}

		return taskQueues;
	}
	
}
