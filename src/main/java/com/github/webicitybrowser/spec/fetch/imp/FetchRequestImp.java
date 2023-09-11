package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.url.URL;

public record FetchRequestImp(String method, URL url) implements FetchRequest {
	
}
