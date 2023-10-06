package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.taskDestination.TaskDestination;

public record FetchParams(FetchRequest request, FetchConsumeBodyAction consumeBodyAction, TaskDestination taskDestination) {
	
}
