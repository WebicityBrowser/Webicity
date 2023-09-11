package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.FetchRequest;

public class FetchParametersBuilderImp implements FetchParametersBuilder {

	private FetchRequest request;
	private FetchConsumeBodyAction consumeBodyAction;

	@Override
	public void setRequest(FetchRequest request) {
		this.request = request;
	}

	@Override
	public void setConsumeBodyAction(FetchConsumeBodyAction consumeBodyAction) {
		this.consumeBodyAction = consumeBodyAction;
	}

	@Override
	public FetchParameters build() {
		return new FetchParametersImp(request, consumeBodyAction);
	}
	
}
