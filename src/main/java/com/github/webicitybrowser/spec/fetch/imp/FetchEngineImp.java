package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.*;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.url.URL;

import java.util.LinkedList;
import java.util.Queue;

public class FetchEngineImp implements FetchEngine {

	private final FetchConnectionPool connectionPool;
	private Queue<FetchConsumeBodyAction> fetchQueue;

	public FetchEngineImp(FetchConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		fetchQueue = new LinkedList<>();
	}

	@Override
	public void fetch(FetchParameters parameters) {
		FetchParams params = new FetchParams(parameters.request(), parameters.consumeBodyAction());
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
				// TODO: Queue a response
				fetchQueue.add(params.consumeBodyAction());
			}
			else {
				params.consumeBodyAction().execute(response, true, new byte[]{});
			}
		}
	}


}
