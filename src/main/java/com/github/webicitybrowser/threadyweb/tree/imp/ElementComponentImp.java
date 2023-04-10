package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementComponentImp extends BaseWebComponent implements ElementComponent {

	public ElementComponentImp(Element foundChild) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return ElementComponent.class;
	}

}
