package com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class NoStylesheetAction implements StylesheetAction{

	@Override
	public void processThisTypeOfLinkedResource(Element el, boolean success, FetchResponse response, byte[] bodyBytes) {

	}

}
