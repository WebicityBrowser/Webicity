package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;

public record FetchParametersImp(
	FetchRequest request, FetchConsumeBodyAction consumeBodyAction
) implements FetchParameters {
	
}
