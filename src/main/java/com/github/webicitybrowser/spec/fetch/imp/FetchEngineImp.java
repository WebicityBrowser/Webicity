package com.github.webicitybrowser.spec.fetch.imp;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchParams;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.stream.ByteStreamReader;
import com.github.webicitybrowser.spec.url.URL;


public class FetchEngineImp implements FetchEngine {

	private final FetchConnectionPool connectionPool;

	public FetchEngineImp(FetchConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public void fetch(FetchParameters parameters) {
		FetchParams params = new FetchParams(parameters.request(), parameters.consumeBodyAction(), parameters.taskDestination());
		mainFetch(params);
	}

	private void mainFetch(FetchParams params) {
		FetchResponse response = httpFetch(params);
		fetchResponseHandover(params, response);
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
			Consumer<byte[]> processBody = nullOrBytes -> params.consumeBodyAction().execute(response, true, nullOrBytes);
			if(response.body() == null) {
				queueAFetchTask(() -> processBody.accept(null), params);
			} else {
				fullyReadBody(response.body(), processBody, params.taskDestination());
			}
		}
	}

	private void queueAFetchTask(Runnable fetchTask, FetchParams params) {
		params.taskDestination().enqueue(fetchTask);
	}

	private void fullyReadBody(Body body, Consumer<byte[]> processBody, TaskDestination taskDestination) {
		try {
			final byte[] allBytes = ByteStreamReader.readAllBytes(body.readableStream());
			Consumer<byte[]> successSteps = bytes -> taskDestination.enqueue(() -> processBody.accept(bytes));
			successSteps.accept(allBytes);
		} catch (Exception e) {
			// TODO
		}
	}

}
