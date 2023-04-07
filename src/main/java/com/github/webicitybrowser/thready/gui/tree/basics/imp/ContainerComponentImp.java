package com.github.webicitybrowser.thready.gui.tree.basics.imp;

import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ContainerComponentImp implements ContainerComponent {

	@Override
	public Class<? extends Component> getPrimaryType() {
		return ContainerComponent.class;
	}

}
