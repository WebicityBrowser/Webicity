package com.github.webicitybrowser.spec.fetch.TaskDestination;

import java.util.LinkedList;
import java.util.Queue;

public class ParallelQueue implements TaskDestination{

	private final Queue<Runnable> tasksQueue;

	public ParallelQueue() {
		tasksQueue = new LinkedList<>();
	}

	@Override
	public void enqueue(Runnable task) {
		tasksQueue.add(task);
	}

	@Override
	public void dequeueAll() {
		while(tasksQueue.isEmpty() == false) {
			tasksQueue.remove().run();
		}
	}

}
