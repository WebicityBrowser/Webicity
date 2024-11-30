package com.github.webicitybrowser.spec.fetch.builder.imp;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;

public class FetchParametersBuilderImp implements FetchParametersBuilder {

	private FetchRequest request;
	private Consumer<FetchResponse> processResponseAction;
	private FetchConsumeBodyAction consumeBodyAction;
	private TaskDestination taskDestination;

	@Override
	public FetchParametersBuilder setRequest(FetchRequest request) {
		this.request = request;

		return this;
	}

	@Override
	public FetchParametersBuilder setProcessResponseAction(Consumer<FetchResponse> processResponseAction) {
		this.processResponseAction = processResponseAction;

		return this;
	}

	@Override
	public FetchParametersBuilder setConsumeBodyAction(FetchConsumeBodyAction consumeBodyAction) {
		this.consumeBodyAction = consumeBodyAction;

		return this;
	}

	@Override
	public FetchParametersBuilder setTaskDestination(TaskDestination taskDestination) {
		this.taskDestination = taskDestination;

		return this;
	}

	@Override
	public FetchParameters build() {
		return new FetchParameters(
			request, processResponseAction,
			consumeBodyAction, taskDestination);
	}
	
}
