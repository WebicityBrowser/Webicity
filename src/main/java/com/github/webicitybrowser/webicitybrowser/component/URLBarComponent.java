package com.github.webicitybrowser.webicitybrowser.component;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class URLBarComponent extends BaseComponent {
	
	private String value = "";
	
	public URLBarComponent setValue(String value) {
		this.value = value;
		// TODO: Fire an event when value changed
		
		return this;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return URLBarComponent.class;
	};
	
}
