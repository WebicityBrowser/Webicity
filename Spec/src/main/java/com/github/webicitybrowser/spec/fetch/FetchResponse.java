package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.FetchNetworkError;

/**
 * A fetch response includes details about a resource that was
 * fetched from a server, as well as details about the process via
 * which the resource was fetched.
 */
public interface FetchResponse {

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

	/**
	 * Create a network error response. This indicates that the
	 * resource could not be fetched due to an error.
	 * @return A network error response.
	 */
	static FetchResponse createNetworkError() {
		return new FetchNetworkError();
	}

}
