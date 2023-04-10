package com.github.webicitybrowser.thready.gui.tree.basics.imp;

import com.github.webicitybrowser.thready.gui.tree.basics.TextComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TextComponentImp extends BaseComponent implements TextComponent {

	private String text = "";
	
	@Override
	public Class<? extends Component> getPrimaryType() {
		return TextComponent.class;
	}

	@Override
	public TextComponent text(String text) {
		this.text = text;
		
		return this;
	}

	@Override
	public String getText() {
		return text;
	}

}
