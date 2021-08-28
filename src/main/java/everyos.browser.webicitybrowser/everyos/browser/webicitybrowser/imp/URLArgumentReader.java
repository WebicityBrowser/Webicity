package everyos.browser.webicitybrowser.imp;

import java.net.MalformedURLException;

import everyos.api.getopts.ArgumentReader;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicity.net.URL;

public class URLArgumentReader implements ArgumentReader<URL> {
	@Override
	public URL transform(String input) throws ParserFailedException {
		try {
			return new URL(input);
		} catch (MalformedURLException e) {
			// TODO: Use parser-specific logger
			System.out.println("Malformed URL: " + input);
			
			throw new ParserFailedException();
		}
	}
}
