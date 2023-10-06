package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.TaskDestination.ParallelQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.url.URL;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.eq;

public class FetchEngineTest {

	private static final byte[] DUMMY_BODY = new byte[] { 1, 2, 3 };
	private static final URL DUMMY_URL = URL.ofSafe("https://www.google.com/");

	@Test
	@DisplayName("Can reach process response consume body")
	public void canReachProcessResponseConsumeBody() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchEngineImp fetchEngineImp = new FetchEngineImp(mockConnectionPool());
		FetchRequest request = FetchRequest.createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		parametersBuilder.setTaskDestination(new ParallelQueue());
		FetchParameters parameters = parametersBuilder.build();
		fetchEngineImp.fetch(parameters);
		fetchEngineImp.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(Mockito.any(), eq(true), Mockito.any());
	}

	@Test
	@DisplayName("Can fetch data with HTTP fetch")
	public void testFetchWithMockData() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchEngine fetchEngine = new FetchEngineImp(mockConnectionPool());
		FetchRequest request = FetchRequest.createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		parametersBuilder.setTaskDestination(new ParallelQueue());
		FetchParameters parameters = parametersBuilder.build();
		fetchEngine.fetch(parameters);
		fetchEngine.fetch(parameters);


		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(eq(mockFetchResponse()), Mockito.anyBoolean(), Mockito.any());

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

				return FetchResponseBuilder.createNetworkError();
			}
		};
	}

	private FetchResponse mockFetchResponse() {
		return new FetchResponse(){
			@Override
			public Body body() {
				return Body.createBody(new InputStreamReader(new ByteArrayInputStream(new byte[] {1,2,3,4})),DUMMY_BODY, -1);
			}

			@Override
			public boolean equals(Object o) {
				FetchResponse fr = (FetchResponse) o;
				return fr.body().length() == this.body().length();
			}
		};
	}

}
