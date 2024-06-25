package com.github.webicitybrowser.webicitybrowser.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class URLBarComponent extends BaseComponent {
	
	private final Supplier<String> valueSupplier;
	private final Consumer<String> action;

	private final List<Runnable> urlChangeListeners = new ArrayList<>();

	public URLBarComponent(Supplier<String> valueSupplier, Consumer<String> action) {
		this.valueSupplier = valueSupplier;
		this.action = action;
	}
	
	@Override
	public Class<? extends Component> getPrimaryType() {
		return URLBarComponent.class;
	};

	public String getValue() {
		return valueSupplier.get();
	}
	
	public void addURLChangeListener(Runnable listener) {
		urlChangeListeners.add(listener);
	}

	public void notifyURLChangeListeners() {
		urlChangeListeners.forEach(Runnable::run);
	}

	public void removeURLChangeListener(Runnable listener) {
		urlChangeListeners.remove(listener);
	}

	public Optional<Consumer<String>> getAction() {
		return Optional.ofNullable(this.action);
	}
	
}
