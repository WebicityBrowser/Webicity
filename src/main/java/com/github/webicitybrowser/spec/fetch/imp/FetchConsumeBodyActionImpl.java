package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchResponseBuilderImp;

public class FetchConsumeBodyActionImpl implements FetchConsumeBodyAction {

	@Override
	public void execute(FetchResponse response, boolean success, byte[] body) {
		System.out.println(response.body());
	}

}
