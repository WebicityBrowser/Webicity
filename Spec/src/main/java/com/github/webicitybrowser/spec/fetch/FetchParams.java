package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;

public record FetchParams(FetchRequest request, FetchConsumeBodyAction consumeBodyAction, TaskDestination taskDestination) {
	
}
