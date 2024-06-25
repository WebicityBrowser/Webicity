package com.github.webicitybrowser.webicity.renderer.backend.html.tasks;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;

public class TaskQueueImp implements TaskQueue {

	private final Deque<Runnable> tasks = new ConcurrentLinkedDeque<>();

	@Override
	public void enqueue(Runnable task) {
		tasks.add(task);
	}

	@Override
	public Optional<Runnable> poll() {
		return Optional.ofNullable(tasks.poll());
	}

}
