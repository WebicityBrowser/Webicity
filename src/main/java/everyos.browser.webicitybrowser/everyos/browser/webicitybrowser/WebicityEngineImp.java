package everyos.browser.webicitybrowser;

import java.io.IOException;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;

public class WebicityEngineImp extends WebicityEngine {
	public WebicityEngineImp() {
		super();
		this.registerDefaultProtocols();
	}
	
	@Override
	public Response processRequest(Request request) throws IOException {
		return request.send();
	}
}
