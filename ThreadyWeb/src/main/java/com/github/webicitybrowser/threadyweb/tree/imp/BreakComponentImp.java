package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;

public class BreakComponentImp extends ElementComponentImp implements BreakComponent {

	public BreakComponentImp(Element element, WebComponentContext componentContext) {
		super(element, componentContext);
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return BreakComponent.class;
	}
	
}
