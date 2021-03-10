package everyos.browser.webicity.renderer;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.WebicityFrame;

public interface Renderer {
	void execute(WebicityFrame frame, InputStream stream) throws IOException;
}
