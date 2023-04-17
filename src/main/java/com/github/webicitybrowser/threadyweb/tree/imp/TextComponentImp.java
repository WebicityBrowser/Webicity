package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public class TextComponentImp extends BaseWebComponent implements TextComponent {
	
	private final Text text;

	public TextComponentImp(Text text) {
		this.text = text;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return TextComponent.class;
	}
	
	@Override
	public Node getNode() {
		return this.text;
	}

	@Override
	public String getText() {
		return text.getData();
	}

}
