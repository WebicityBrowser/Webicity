package com.github.webicitybrowser.spec.fetch;

import java.util.List;

import com.github.webicitybrowser.spec.fetch.builder.FetchRequestBuilder;
import com.github.webicitybrowser.spec.url.URL;

public record FetchRequest(String method, URL url) {

	public List<URL> urlList() {
		return List.of(url);
	}

	public static FetchRequest createRequest(String method, URL url) {
		FetchRequestBuilder fetchRequestBuilder = FetchRequestBuilder.create();
		fetchRequestBuilder.setMethod(method);
		fetchRequestBuilder.setUrl(url);

		return fetchRequestBuilder.build();
	}

}
