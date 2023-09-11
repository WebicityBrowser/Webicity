package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.FetchParametersBuilderImp;

public interface FetchParametersBuilder {
	
	void setRequest(FetchRequest request);

	void setConsumeBodyAction(FetchConsumeBodyAction consumeBodyAction);

	FetchParameters build();

	static FetchParametersBuilder create() {
		return new FetchParametersBuilderImp();
	}

}
