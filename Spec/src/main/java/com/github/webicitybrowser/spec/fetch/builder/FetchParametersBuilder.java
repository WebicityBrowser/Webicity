package com.github.webicitybrowser.spec.fetch.builder;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchParametersBuilderImp;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;

public interface FetchParametersBuilder {
	
	FetchParametersBuilder setRequest(FetchRequest request);

	FetchParametersBuilder setProcessResponseAction(Consumer<FetchResponse> processResponseAction);

	FetchParametersBuilder setConsumeBodyAction(FetchConsumeBodyAction consumeBodyAction);

	FetchParametersBuilder setTaskDestination(TaskDestination taskDestination);

	FetchParameters build();

	static FetchParametersBuilder create() {
		return new FetchParametersBuilderImp();
	}

}
