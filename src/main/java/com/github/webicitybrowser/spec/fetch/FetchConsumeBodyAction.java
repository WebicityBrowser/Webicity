package com.github.webicitybrowser.spec.fetch;

public interface FetchConsumeBodyAction {


	void execute(FetchResponse response, boolean success, byte[] body);
	FetchResponse getFetchResponse();

}
