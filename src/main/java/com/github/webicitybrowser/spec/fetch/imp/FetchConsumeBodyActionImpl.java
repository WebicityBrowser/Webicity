package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchResponseBuilderImp;

public class FetchConsumeBodyActionImpl implements FetchConsumeBodyAction {
	private FetchResponse fetchResponse;
	@Override
	public void execute(FetchResponse response, boolean success, byte[] body) {
		FetchResponseBuilder fetchResponseBuilder = new FetchResponseBuilderImp();
		fetchResponseBuilder.setBody(response.body());
		fetchResponse = fetchResponseBuilder.build();
	}

	@Override
	public FetchResponse getFetchResponse() {
		return fetchResponse;
	}
}
