package com.github.webicitybrowser.webicitybrowser.imp;


import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;

import everyos.api.getopts.ArgumentReader;
import everyos.api.getopts.ErrorHandler;
import everyos.api.getopts.ParserFailedException;

public class URLArgumentReader implements ArgumentReader<URL> {
	
	@Override
	public URL transform(String input, ErrorHandler errorHandler) throws ParserFailedException {
		try {
			return URL.of(input);
		} catch (InvalidURLException e) {
			errorHandler.error("Malformed URL: \"" + input + '"');
			
			throw new ParserFailedException();
		}
	}
	
}