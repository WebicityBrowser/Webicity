package com.github.webicitybrowser.spec.fetch;

/**
 * A list of headers that were sent with a response.
 * A header is a way of relaying metadata of the response through
 * the usage of a string -> string map-like structure.
 */
public interface FetchHeaderList {

	/**
	 * Get the value held by the header that is identified
	 * by the given name.
	 * Returns null for headers that do not exist. You can use
	 * Optional.ofNullable to convert the return value to an
	 * Optional for simpler and less error-prone usage.
	 * @param headerName The name of the header to get the value of.
	 * @return The value of the header, or null if the header does not exist.
	 */
	String getHeaderValue(String headerName);

}
