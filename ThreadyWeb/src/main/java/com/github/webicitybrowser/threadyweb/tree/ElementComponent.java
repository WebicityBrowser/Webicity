package com.github.webicitybrowser.threadyweb.tree;

import java.util.List;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.imp.ElementComponentImp;

public interface ElementComponent extends WebComponent {

	List<Component> getChildren();

	Element getNode();

    CSSRuleList getComponentRules();

	public static WebComponent create(Element element, WebComponentContext componentContext) {
		return new ElementComponentImp(element, componentContext);
	}

}
