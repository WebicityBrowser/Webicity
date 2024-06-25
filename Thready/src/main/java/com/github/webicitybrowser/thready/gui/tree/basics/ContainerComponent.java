package com.github.webicitybrowser.thready.gui.tree.basics;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.ContainerComponentImp;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface ContainerComponent extends Component {

	public static ContainerComponent create() {
		return new ContainerComponentImp();
	}
	
}
