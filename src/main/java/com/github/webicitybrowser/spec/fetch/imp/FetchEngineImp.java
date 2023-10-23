package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.*;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.stream.ByteStreamReader;
import com.github.webicitybrowser.spec.url.URL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


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
		FetchResponse response = null;
		if(params.request().url().getScheme().equals("webicity")) {
			response = new FetchResponseImp(new BodyImp(
				new InputStreamReader(
					ClassLoader.getSystemClassLoader().getResourceAsStream("." + params.request().url().getPath())
				) ,new byte[] {})
			);
		}
		else if(params.request().url().getScheme().equals("file")) {
			try {
				System.out.println(params.request().url());
				response = new FetchResponseImp(new BodyImp(
					new InputStreamReader(new FileInputStream(params.request().url().getPath())), new byte[] {}
				));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		else if(params.request().url().getScheme().equals("http") || params.request().url().getScheme().equals("https")){
			response = httpFetch(params);
		}
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
