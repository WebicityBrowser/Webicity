package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;

public class BreakComponentImp extends ElementComponentImp implements BreakComponent {

	public BreakComponentImp(Element element) {
		super(element);
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return BreakComponent.class;
	}
	
}
