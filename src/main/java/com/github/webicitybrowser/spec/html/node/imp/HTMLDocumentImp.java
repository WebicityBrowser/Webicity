package com.github.webicitybrowser.spec.html.node.imp;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.stylesheet.StyleSheetList;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentImp;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.spec.html.node.HTMLHeadElement;
import com.github.webicitybrowser.spec.html.node.HTMLHtmlElement;
import com.github.webicitybrowser.spec.html.node.HTMLTitleElement;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public class HTMLDocumentImp extends DocumentImp implements HTMLDocument {
	
	private final StyleSheetList styleSheetList = StyleSheetList.create();

	@Override
	public String getTitle() {
		HTMLTitleElement titleElement = getTitleElement();
		String value = titleElement == null ?
			"" :
			titleElement.getText();
		return ASCIIUtil.stripAndCollapseASCIIWhitespace(value);
	}

	private HTMLTitleElement getTitleElement() {
		HTMLHeadElement headElement = getHeadElement();
		return (HTMLTitleElement) scan(headElement, child -> child instanceof HTMLTitleElement);
	}

	private HTMLHeadElement getHeadElement() {
		HTMLHtmlElement htmlElement = getHtmlElement();
		return (HTMLHeadElement) scan(htmlElement, child -> child instanceof HTMLHeadElement);
	}
	
	private HTMLHtmlElement getHtmlElement() {
		return (HTMLHtmlElement) scan(this, child -> child instanceof HTMLHtmlElement);
	}

	private Node scan(Node parent, Function<Node, Boolean> filter) {
		for (Node child: parent.getChildNodes()) {
			if (filter.apply(child)) {
				return child;
			}
		}
		
		return null;
	}

	@Override
	public StyleSheetList getStyleSheets() {
		return this.styleSheetList;
	}

}
