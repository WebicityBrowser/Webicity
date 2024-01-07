package com.github.webicitybrowser.spec.fetch;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.fetch.test.DummyTaskDestination;
import com.github.webicitybrowser.spec.url.URL;


public class FetchEngineTest {

	private static final byte[] DUMMY_BODY = new byte[] { 1, 2, 3 };
	private static final URL DUMMY_URL = URL.ofSafe("https://www.google.com/");

	@Test
	@DisplayName("Can reach process response consume body")
	public void canReachProcessResponseConsumeBody() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		FetchProtocolRegistry registry = Mockito.mock(FetchProtocolRegistry.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchEngineImp fetchEngineImp = new FetchEngineImp(mockConnectionPool(), registry, task -> task.run());
		FetchRequest request = FetchRequest.createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		parametersBuilder.setTaskDestination(new DummyTaskDestination());
		FetchParameters parameters = parametersBuilder.build();
		fetchEngineImp.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(Mockito.any(), Mockito.eq(true), Mockito.any());
	}

	@Test
	@DisplayName("Can fetch data with HTTP fetch")
	public void testFetchWithMockData() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doAnswer(invocation -> {
			FetchResponse response = invocation.getArgument(1);
			FetchBody body = response.body();
			byte[] bodyBytes = body.readableStream().readAllBytes();
			Assertions.assertArrayEquals(DUMMY_BODY, bodyBytes);

			return null;
		}).when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchProtocolRegistry registry = Mockito.mock(FetchProtocolRegistry.class);
		FetchEngine fetchEngine = new FetchEngineImp(mockConnectionPool(), registry, task -> task.run());
		FetchRequest request = FetchRequest.createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		parametersBuilder.setTaskDestination(new DummyTaskDestination());
		FetchParameters parameters = parametersBuilder.build();
		fetchEngine.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1)).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());
	}
	

	private FetchConnectionPool mockConnectionPool() {
		return new FetchConnectionPool() {
			@Override
			public void close() throws Exception {}

			@Override
			public FetchConnection createNewConnection(FetchConnectionInfo info) {
				return mockFetchConnection(info);
			}
		};
	}

	private FetchConnection mockFetchConnection(FetchConnectionInfo info) {
		return new FetchConnection() {
			@Override
			public FetchConnectionInfo info() {
				return info;
			}

			@Override
			public FetchResponse send(FetchRequest request) {
				if (request.url().equals(DUMMY_URL) && request.method().equals("GET")) {
					return mockFetchResponse();
				}

				return FetchResponse.createNetworkError();
			}
		};
	}

	private FetchResponse mockFetchResponse() {
		FetchResponse response = Mockito.mock(FetchResponse.class);
		FetchBody mockBody = FetchBody.createBody(new ByteArrayInputStream(DUMMY_BODY), null);
		Mockito.when(response.body()).thenReturn(mockBody);
		
		return response;
	}

}
