package com.github.webicitybrowser.spec.fetch.taskdestination;

import java.util.LinkedList;
import java.util.Queue;

public class ParallelQueue implements TaskDestination{

	private final Queue<Runnable> tasksQueue;

	// This class won't be used after merge with another branch
	public ParallelQueue() {
		tasksQueue = new LinkedList<>();
	}

	@Override
	public void enqueue(Runnable task) {
		//tasksQueue.add(task);
		task.run();
	}

	@Override
	public void dequeueAll() {
		while(tasksQueue.isEmpty() == false) {
			tasksQueue.remove().run();
		}
	}

}
