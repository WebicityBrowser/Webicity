package com.github.webicitybrowser.spec.fetch;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;

public interface FetchProtocolRegistry {

	Optional<Reader> openConnection(URL url) throws IOException;

}
