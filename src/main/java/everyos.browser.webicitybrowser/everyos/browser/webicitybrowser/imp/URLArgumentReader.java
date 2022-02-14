package everyos.browser.webicitybrowser.imp;

import java.net.MalformedURLException;

import everyos.api.getopts.ArgumentReader;
import everyos.api.getopts.ErrorHandler;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.spec.jnet.URL;

public class URLArgumentReader implements ArgumentReader<URL> {
	@Override
	public URL transform(String input, ErrorHandler errorHandler) throws ParserFailedException {
		try {
			return new URL(input);
		} catch (MalformedURLException e) {
			errorHandler.error("Malformed URL: \"" + input + '"');
			
			throw new ParserFailedException();
		}
	}
}
