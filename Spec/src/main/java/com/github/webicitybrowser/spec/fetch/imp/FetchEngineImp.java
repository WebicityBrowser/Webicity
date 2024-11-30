package com.github.webicitybrowser.spec.fetch.imp;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchBody.FetchBodyWithType;
import com.github.webicitybrowser.spec.fetch.FetchDecoder;
import com.github.webicitybrowser.spec.fetch.FetchDecoderRegistry;
import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchParams;
import com.github.webicitybrowser.spec.fetch.FetchProtocolRegistry;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.FetchResponse.MessageStream;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.fetch.imp.DataURLProcessor.DataURLStruct;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.htmlbrowsers.ParallelContext;
import com.github.webicitybrowser.spec.stream.ReadableStream;
import com.github.webicitybrowser.spec.stream.ReadableStreamDefaultReader;
import com.github.webicitybrowser.spec.url.URL;

public class FetchEngineImp implements FetchEngine {

	private final FetchConnectionPool connectionPool;
	private final FetchProtocolRegistry fetchProtocolRegistry;
	private final FetchDecoderRegistry decoderRegisty;
	private final ParallelContext parallelContext;

	public FetchEngineImp(
		FetchConnectionPool connectionPool, FetchProtocolRegistry fetchProtocolRegistry,
		FetchDecoderRegistry decoderRegisty, ParallelContext parallelContext
	) {
		this.connectionPool = connectionPool;
		this.fetchProtocolRegistry = fetchProtocolRegistry;
		this.decoderRegisty = decoderRegisty;
		this.parallelContext = parallelContext;
	}

	@Override
	public void fetch(FetchParameters parameters) {
		FetchParams params = new FetchParams(
			parameters.request(), parameters.processResponseAction(),
			parameters.consumeBodyAction(), parameters.taskDestination()
		);
		mainFetch(params);
	}

	private void mainFetch(FetchParams params) {
		parallelContext.inParallel(() -> {
			FetchResponse response;
			if(params.request().url().getScheme().equals("http") || params.request().url().getScheme().equals("https")) {
				response = httpFetch(params);
			} else {
				response = schemeFetch(params);
			}
			fetchResponseHandover(params, response);
		});
	}

	private FetchResponse schemeFetch(FetchParams params) {
		FetchRequest request = params.request();
		URL url = request.url();
		switch(url.getScheme()) {
		case "data":
			Optional<DataURLStruct> struct = DataURLProcessor.processDataURL(url);
			if (struct.isEmpty()) return FetchResponse.createNetworkError(request);
			return new FetchResponseImp(
				safelyExtract(struct.get().body()).body(),
				request.urlList(),
				new EmptyFetchHeaderListImp());
		case "http":
		case "https":
			return httpFetch(params);
		default:
			break;
		}

		try {
			Optional<InputStream> inputStream = fetchProtocolRegistry.openConnection(url);
			if (inputStream.isEmpty()) return FetchResponse.createNetworkError(request);

			// TODO: Do this better
			return new FetchResponseImp(
				safelyExtract(inputStream.get().readAllBytes()).body(),
				request.urlList(),
				new EmptyFetchHeaderListImp());
		} catch (Exception e) {
			return FetchResponse.createNetworkError(request);
		}
	}

	private FetchResponse httpFetch(FetchParams params) {
		return httpNetworkFetch(params);
	}

	private FetchResponse httpNetworkFetch(FetchParams params) {
		FetchNetworkPartitionKey key = FetchConnectionMethods.determineNetworkPartitionKey(params.request());
		URL url = params.request().url();
		FetchConnection connection = FetchConnectionMethods.obtainConnection(connectionPool, key, url);
		FetchResponse response = connection.send(params.request());
		ReadableStream stream = ReadableStream.create();

		response.setBody(FetchBody.createBody(stream, null));

		parallelContext.inParallel(() -> {
			MessageStream messageStream = response.getMessageStream().get();
			List<FetchDecoder> decoders = decoderRegisty.getDecoders(response.headerList());
			while (!messageStream.done()) {
				byte[] bytes = messageStream.read();
				for (FetchDecoder decoder : decoders) {
					bytes = decoder.translate(bytes);
				}
				stream.enqueue(bytes);
			}
			// TODO: What if aborted? Also, content coding
			// TODO: Check if stream readable
			for (FetchDecoder decoder : decoders) {
				decoder.close();
			}
			stream.close();
		});

		return response;
	}

	private void fetchResponseHandover(FetchParams params, FetchResponse response) {
		if (params.processResponseAction() != null) {
			queueAFetchTask(() -> params.processResponseAction().accept(response), params);
		}
		if (params.consumeBodyAction() != null) {
			Consumer<byte[]> processBody = nullOrBytes -> params.consumeBodyAction().execute(response, true, nullOrBytes);
			// TODO: Error handling
			if(response.body() == null) {
				queueAFetchTask(() -> processBody.accept(null), params);
			} else {
				fullyReadBody(response.body(), processBody, null, params.taskDestination());
			}
		}
	}

	private void queueAFetchTask(Runnable fetchTask, FetchParams params) {
		params.taskDestination().enqueue(fetchTask);
	}

	private	FetchBodyWithType safelyExtract(Object object) {
		return extract(object);
	}

	private FetchBodyWithType extract(Object object) {
		ReadableStream stream;
		if (object instanceof ReadableStream) {
			stream = (ReadableStream) object;
		} else {
			stream = ReadableStream.create();
		}

		Supplier<byte[]> action = null;
		Object source;
		if (object instanceof byte[] byteArray) {
			source = byteArray;
			// TODO: Length
		} else {
			throw new IllegalArgumentException("Unknown body type: " + object.getClass());
		}

		if (source instanceof byte[]) {
			action = () -> (byte[]) source;
		}

		if (action != null) {
			byte[] bytes = action.get();
			// TODO: Convert to UInt8Array
			stream.enqueue(bytes);
			stream.close();
		}

		return new FetchBodyWithType(FetchBody.createBody(stream, source), null);
	}

	private void fullyReadBody(FetchBody body, Consumer<byte[]> processBody, Consumer<Exception> processBodyError, TaskDestination taskDestination) {
		// TODO: What if TaskDestination is null?
		Consumer<byte[]> successSteps = bytes -> taskDestination.enqueue(() -> processBody.accept(bytes));
		Consumer<Exception> errorSteps = exception -> taskDestination.enqueue(() -> processBodyError.accept(exception));
		ReadableStreamDefaultReader reader = null;
		try {
			reader = ReadableStreamDefaultReader.acquire(body.stream());
		} catch (Exception e) {
			// TODO: Specifically TypeError
			errorSteps.accept(e);
			e.printStackTrace();
			return;
		}
		reader.readAllBytes(successSteps, errorSteps);
	}

}
