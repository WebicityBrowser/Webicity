package everyos.browser.webicity.net;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.renderer.Renderer;

public abstract class Response {
	public abstract int getStatus();
	public abstract InputStream getConnection() throws IOException;
	public abstract Renderer getProbableRenderer();
	//public abstract String getPreferredEncoding();
}
