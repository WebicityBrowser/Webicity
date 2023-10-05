package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.builder.imp.FetchResponseBuilderImp;
import com.github.webicitybrowser.spec.fetch.connection.imp.FetchConnectionImp;
import com.github.webicitybrowser.spec.fetch.imp.BodyImp;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.encoding.chunked.ChunkedEncoding;
import com.github.webicitybrowser.spec.http.imp.HTTPServiceImp;
import com.github.webicitybrowser.spec.http.version.http11.HTTP11Version;
import com.github.webicitybrowser.webicitybrowser.engine.net.SocketChannelHTTPTransportFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.builder.FetchRequestBuilder;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.url.URL;

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
		FetchRequest request = createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		FetchParameters parameters = parametersBuilder.build();
		fetchEngineImp.fetch(parameters);

		Mockito.verify(consumeBodyAction, Mockito.times(1))
			.execute(Mockito.any(), eq(true), Mockito.any());
	}

	@Test
	public void testFetchWithMockData() {
		FetchConsumeBodyAction consumeBodyAction = Mockito.mock(FetchConsumeBodyAction.class);
		Mockito.doNothing().when(consumeBodyAction).execute(Mockito.any(), Mockito.anyBoolean(), Mockito.any());

		FetchEngine fetchEngine = new FetchEngineImp(new FetchConnectionPool() {
			@Override
			public FetchConnection createNewConnection(FetchConnectionInfo info) {
				HTTPService service = HTTPService.create("Webicity/0.1.0 ThreadyWeb/0.1.0 Firefox/113.0 (Not actually Firefox)");
				service.setTransportFactory(new SocketChannelHTTPTransportFactory());
				service.registerHTTPVersion(new HTTP11Version());
				service.registerTransferEncoding(new ChunkedEncoding());
				return new FetchConnectionImp(info, service);
			}

			@Override
			public void close() throws Exception {

			}
		});

		FetchRequest request = createRequest("GET", DUMMY_URL);
		FetchParametersBuilder parametersBuilder = FetchParametersBuilder.create();
		parametersBuilder.setRequest(request);
		parametersBuilder.setConsumeBodyAction(consumeBodyAction);
		FetchParameters parameters = parametersBuilder.build();
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
				return Body.createBody(null,DUMMY_BODY, -1);
			}
		};
	}

	private FetchRequest createRequest(String method, URL url) {
		FetchRequestBuilder fetchRequestBuilder = FetchRequestBuilder.create();
		fetchRequestBuilder.setMethod(method);
		fetchRequestBuilder.setUrl(url);

		return fetchRequestBuilder.build();
	}

}
