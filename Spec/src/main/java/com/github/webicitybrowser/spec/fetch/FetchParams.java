package com.github.webicitybrowser.spec.fetch;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;

public record FetchParams(
	FetchRequest request, Consumer<FetchResponse> processResponseAction,
	FetchConsumeBodyAction consumeBodyAction, TaskDestination taskDestination
) {
	
}
