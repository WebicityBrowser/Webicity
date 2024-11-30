package com.github.webicitybrowser.spec.fetch;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.fetch.FetchResponse.MessageStream;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.fetch.test.DummyTaskDestination;
import com.github.webicitybrowser.spec.stream.ReadableStream;
import com.github.webicitybrowser.spec.url.URL;


public class FetchEngineTest {

	private static final byte[] DUMMY_BODY = new byte[] { 1, 2, 3 };
	private static final URL DUMMY_URL = URL.ofSafe("https://www.google.com/");

	private FetchEngine fetchEngine;

	@BeforeEach
	public void setup() {
		FetchProtocolRegistry registry = Mockito.mock(FetchProtocolRegistry.class);
		FetchDecoderRegistry decoderRegistry = Mockito.mock(FetchDecoderRegistry.class);
		Mockito.when(decoderRegistry.getDecoder(Mockito.any())).thenReturn(null);
		fetchEngine = new FetchEngineImp(mockConnectionPool(), registry, decoderRegistry, task -> task.run());
	}

	@Test
	@DisplayName("Can reach process response consume body")
	public void canReachProcessResponseConsumeBody() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchRequest request = FetchRequest.createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		parametersBuilder.setTaskDestination(new DummyTaskDestination());
		FetchParameters parameters = parametersBuilder.build();
		fetchEngine.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(Mockito.any(), Mockito.eq(true), Mockito.any());
	}

	@Test
	@DisplayName("Can fetch data with HTTP fetch")
	public void testFetchWithMockData() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doAnswer(invocation -> {
			byte[] bodyBytes = invocation.getArgument(2);
			Assertions.assertArrayEquals(DUMMY_BODY, bodyBytes);

			return null;
		}).when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

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

				return FetchResponse.createNetworkError(request);
			}
		};
	}

	private FetchResponse mockFetchResponse() {
		FetchResponse response = Mockito.mock(FetchResponse.class);
		ReadableStream stream = ReadableStream.create();
		stream.enqueue(DUMMY_BODY);
		MessageStream messageStream = Mockito.mock(MessageStream.class);
		AtomicBoolean done = new AtomicBoolean(false);
		Mockito.when(messageStream.read()).thenAnswer(invocation -> {
			done.set(true);
			return DUMMY_BODY;
		});
		Mockito.when(messageStream.done()).thenAnswer(invocation -> done.get());
		Mockito.when(response.getMessageStream()).thenReturn(Optional.of(messageStream));

		AtomicReference<FetchBody> body = new AtomicReference<>();
		Mockito.when(response.body()).thenAnswer(invocation -> body.get());
		Mockito.doAnswer(invocation -> {
			body.set(invocation.getArgument(0));
			return null;
		}).when(response).setBody(Mockito.any());
		
		return response;
	}

}
