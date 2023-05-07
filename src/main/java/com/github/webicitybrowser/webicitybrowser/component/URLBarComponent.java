package com.github.webicitybrowser.webicitybrowser.component;

import java.util.Optional;
import java.util.function.Consumer;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class URLBarComponent extends BaseComponent {
	
	private String value = "";
	private Consumer<String> action;
	
	@Override
	public Class<? extends Component> getPrimaryType() {
		return URLBarComponent.class;
	};
	
	public URLBarComponent setValue(String value) {
		this.value = value;
		// TODO: Fire an event when value changed
		
		return this;
	}

	public String getValue() {
		return this.value;
	}
	
	public void setAction(Consumer<String> action) {
		this.action = action;
	}
	
	public Optional<Consumer<String>> getAction() {
		return Optional.ofNullable(this.action);
	}
	
}
