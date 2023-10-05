package com.github.webicitybrowser.spec.fetch.TaskDestination;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;


public interface TaskDestination {

	void enqueue(Runnable task);

}
