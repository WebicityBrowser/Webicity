package com.github.webicitybrowser.spec.http;

import java.io.IOException;

import com.github.webicitybrowser.spec.url.URL;

public interface HTTPTransportFactory {

	HTTPTransport createTransport(URL url) throws IOException;

}
