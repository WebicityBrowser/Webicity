package com.github.webicitybrowser.spec.fetch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;

public interface FetchProtocolRegistry {

	Optional<InputStream> openConnection(URL url) throws IOException;

}
