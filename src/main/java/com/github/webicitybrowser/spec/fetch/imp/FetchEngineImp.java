package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchParams;
import com.github.webicitybrowser.spec.fetch.FetchProtocol;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.stream.ByteStreamReader;
import com.github.webicitybrowser.spec.url.URL;

import java.io.Reader;


public class FetchEngineImp implements FetchEngine {

	private final FetchConnectionPool connectionPool;

	private final FetchProtocol fetchProtocol;

	public FetchEngineImp(FetchConnectionPool connectionPool, FetchProtocol fetchProtocol) {
		this.connectionPool = connectionPool;
		this.fetchProtocol = fetchProtocol;
	}

	@Override
	public void fetch(FetchParameters parameters) {
		FetchParams params = new FetchParams(parameters.request(), parameters.consumeBodyAction(), parameters.taskDestination());
		mainFetch(params);
	}

	private void mainFetch(FetchParams params) {
		FetchResponse response;
		if(params.request().url().getScheme().equals("http") || params.request().url().getScheme().equals("https")) {
			response = httpFetch(params);
		} else {
			response = schemeFetch(params);
		}
		fetchResponseHandover(params, response);
	}

	private FetchResponse schemeFetch(FetchParams params) {
		try {
			Reader streamReader = fetchProtocol.registry().getProtocolForURL(params.request().url()).get().openConnection(
				params.request().url(), fetchProtocol.context()).getInputReader();

			return new FetchResponseImp(Body.createBody( streamReader, new byte[] {}));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private FetchResponse httpFetch(FetchParams params) {
		return httpNetworkFetch(params);
	}

	private FetchResponse httpNetworkFetch(FetchParams params) {
		FetchNetworkPartitionKey key = FetchConnectionMethods.determineNetworkPartitionKey(params.request());
		URL url = params.request().url();
		FetchConnection connection = FetchConnectionMethods.obtainConnection(connectionPool, key, url);
		return connection.send(params.request());
	}

	private void fetchResponseHandover(FetchParams params, FetchResponse response) {
		if (params.consumeBodyAction() != null) {
			if(response.body() == null) {
				queueAFetchTask(() -> params.consumeBodyAction().execute(response, true, new byte[] {}), params);
			} else {
				fullyReadBody(params, response);
			}
		}
	}

	private void queueAFetchTask(Runnable fetchTask, FetchParams params) {
		params.taskDestination().enqueue(fetchTask);
	}

	private void fullyReadBody(FetchParams params, FetchResponse response) {
		params.consumeBodyAction().execute(response, true, new byte[] {});
		try {
			final byte[] allBytes =  ByteStreamReader.readAllBytes(response.body().readableStream());
			params.taskDestination().enqueue(
				() -> params.consumeBodyAction().execute(response, true, allBytes)
			);
		} catch (Exception e) {
			params.taskDestination().enqueue(() -> params.consumeBodyAction().execute(response, false, new byte[] {}));
		}
	}

}
