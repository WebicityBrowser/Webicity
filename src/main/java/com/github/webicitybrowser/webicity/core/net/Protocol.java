package com.github.webicitybrowser.webicity.core.net;

import java.io.IOException;

import com.github.webicitybrowser.spec.url.URL;

public interface Protocol {
	
	String[] getSchemes();

	Connection openConnection(URL url) throws IOException;
	
}
