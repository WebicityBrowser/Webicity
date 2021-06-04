package everyos.browser.webicity.net;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.renderer.Renderer;

public interface Response {
	public int getStatus();
	public InputStream getConnection() throws IOException;
	public Renderer getProbableRenderer();
	//public String getPreferredEncoding();
}
