package com.github.webicitybrowser.thready.gui.tree.basics;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.TextComponentImp;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface TextComponent extends Component {

	TextComponent text(String text);
	
	String getText();
	
	public static TextComponent create() {
		return new TextComponentImp();
	}
	
}
