package com.github.webicitybrowser.spec.fetch.builder;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchParametersBuilderImp;

public interface FetchParametersBuilder {
	
	FetchParametersBuilder setRequest(FetchRequest request);

	FetchParametersBuilder setConsumeBodyAction(FetchConsumeBodyAction consumeBodyAction);

	FetchParametersBuilder setTaskDestination(TaskDestination taskDestination);

	FetchParameters build();

	static FetchParametersBuilder create() {
		return new FetchParametersBuilderImp();
	}

}
