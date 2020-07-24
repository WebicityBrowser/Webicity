package everyos.browser.webicity.renderer;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.WebicityFrame;

public interface Renderer {
	//TODO: Frame should be WebicityFrame, not WebicityFrame.innerFrame
	public void execute(WebicityFrame frame, InputStream connection) throws IOException;
}
