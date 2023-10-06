package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.taskDestination.TaskDestination;

public record FetchParameters(
	FetchRequest request, FetchConsumeBodyAction consumeBodyAction, TaskDestination taskDestination
) {
	
}
