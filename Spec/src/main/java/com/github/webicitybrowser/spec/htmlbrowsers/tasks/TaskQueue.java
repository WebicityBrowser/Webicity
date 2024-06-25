package com.github.webicitybrowser.spec.htmlbrowsers.tasks;

import java.util.Optional;

public interface TaskQueue {
	
	void enqueue(Runnable task);

	Optional<Runnable> poll();

}
