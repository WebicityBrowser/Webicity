package com.github.webicitybrowser.spec.fetch;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.fetch.imp.FetchNetworkError;
import com.github.webicitybrowser.spec.url.URL;

/**
 * A fetch response includes details about a resource that was
 * fetched from a server, as well as details about the process via
 * which the resource was fetched.
 */
public interface FetchResponse {

	/**
	 * Get the URL of the response.
	 * @return The URL of the response.
	 */
	URL url();

	/**
	 * Get the URL list of the response, the last of which is the
	 * main URL of the response.
	 * @return The URL list of the response.
	 */
	List<URL> urlList();

	/**
	 * Get the body of the response.
	 * @return The body of the response.
	 */
	FetchBody body();

	/**
	 * Get the header list, which contains the headers sent
	 * with the response. They contain metadata about the response.
	 * @return The header list.
	 */
	FetchHeaderList headerList();

	// Internal
	default Optional<MessageStream> getMessageStream() {
		return Optional.empty();
	};

    void setBody(FetchBody body);

	/**
	 * Create a network error response. This indicates that the
	 * resource could not be fetched due to an error.
	 * @param request The request that caused the error.
	 * @return A network error response.
	 */
	static FetchResponse createNetworkError(FetchRequest request) {
		return new FetchNetworkError(request.urlList());
	}

	public static interface MessageStream {
		byte[] read();
		boolean done();
	}

}
