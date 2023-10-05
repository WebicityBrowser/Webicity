package com.github.webicitybrowser.spec.fetch.TaskDestination;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;

import java.util.LinkedList;
import java.util.Queue;

public class ParallelQueue implements TaskDestination{

	private final Queue<FetchConsumeBodyAction> fetchTasks;

	public ParallelQueue() {
		fetchTasks = new LinkedList<>();
	}

	@Override
	public void enqueue(FetchConsumeBodyAction algorithm) {
		fetchTasks.add(algorithm);
	}

}
