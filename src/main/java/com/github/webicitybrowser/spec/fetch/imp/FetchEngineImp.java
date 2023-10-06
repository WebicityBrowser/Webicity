package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchParams;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.TaskDestination.ParallelQueue;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.url.URL;

import java.io.*;
import java.nio.Buffer;

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
		//TODO: implement fully read
		params.taskDestination().dequeueAll();

		try {
			int next;
			BufferedReader br = new BufferedReader(response.body().readableStream());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			while((next = br.read()) != -1) {
				outputStream.write(next);
			}
			params.taskDestination().enqueue(
				() -> params.consumeBodyAction().execute(response, true, outputStream.toByteArray())
			);
		} catch (Exception e) {
			params.taskDestination().enqueue(() -> params.consumeBodyAction().execute(response, false, new byte[] {}));
		}

	}

}
