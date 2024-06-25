package com.github.webicitybrowser.spec.fetch.builder.imp;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;

public class FetchParametersBuilderImp implements FetchParametersBuilder {

	private FetchRequest request;
	private FetchConsumeBodyAction consumeBodyAction;
	private TaskDestination taskDestination;

	@Override
	public FetchParametersBuilder setRequest(FetchRequest request) {
		this.request = request;

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
		return new FetchParameters(request, consumeBodyAction, taskDestination);
	}
	
}
