package everyos.web.spec.uri.parser.state;

import java.net.MalformedURLException;

public interface URLParseState {

	URLParseState parse(int ch) throws MalformedURLException;
	
}
