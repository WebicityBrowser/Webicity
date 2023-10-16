package com.github.webicitybrowser.spec.htmlbrowsers.tasks;

public interface EventLoop {

	public static final int NETWORK_TASK_QUEUE = 0;

	public static final int TYPES_OF_TASK_QUEUES = 1;
	
	TaskQueue getTaskQueue(int id);
	
}
