package everyos.browser.webicity.net;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.renderer.Renderer;

public interface Response {
	
	int getStatus();
	
	InputStream getConnection() throws IOException;
	
	Renderer getProbableRenderer();
	
	//String getPreferredEncoding();
	
}
