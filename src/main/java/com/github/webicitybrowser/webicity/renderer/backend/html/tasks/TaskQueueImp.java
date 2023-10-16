package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;

public class TaskQueueImp implements TaskQueue {

	private final Deque<Runnable> tasks = new ArrayDeque<>();

	@Override
	public void enqueue(Runnable task) {
		tasks.add(task);
	}

	@Override
	public Optional<Runnable> poll() {
		if (tasks.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(tasks.poll());
	}

}
