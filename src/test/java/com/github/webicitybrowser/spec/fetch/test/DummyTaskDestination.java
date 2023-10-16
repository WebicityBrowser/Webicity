package com.github.webicitybrowser.spec.fetch.test;

import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;

public class DummyTaskDestination implements TaskDestination {

	@Override
	public void enqueue(Runnable task) {
		task.run();
	}
	
}
