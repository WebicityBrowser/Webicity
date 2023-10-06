package com.github.webicitybrowser.spec.fetch.taskDestination;


public interface TaskDestination {

	void enqueue(Runnable task);

	void dequeueAll();

}
