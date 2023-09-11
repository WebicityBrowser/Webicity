package com.github.webicitybrowser.spec.fetch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.url.URL;

public class FetchEngineTest {

	private static final byte[] DUMMY_BODY = new byte[] { 1, 2, 3 };

	/*
	@Test
	@DisplayName("Can reach process response consume body")
	public void canReachProcessResponseConsumeBody() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());
		FetchEngineImp fetchEngineImp = new FetchEngineImp();
		FetchRequest request = createRequest("GET", URL.ofSafe("https://www.google.com/"));
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		FetchParameters parameters = parametersBuilder.build();
		fetchEngineImp.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(Mockito.any(), true, Mockito.any());
	}
	*/
	

	private FetchRequest createRequest(String method, URL url) {
		FetchRequestBuilder fetchRequestBuilder = FetchRequestBuilder.create();
		fetchRequestBuilder.setMethod(method);
		fetchRequestBuilder.setUrl(url);

		return fetchRequestBuilder.build();
	}

}
