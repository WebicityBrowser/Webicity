package com.github.webicitybrowser.spec.fetch.TaskDestination;


public interface TaskDestination {

	void enqueue(Runnable task);

	void dequeueAll();

}
