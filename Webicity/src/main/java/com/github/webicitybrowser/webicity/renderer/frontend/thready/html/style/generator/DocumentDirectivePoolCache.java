package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public class DocumentDirectivePoolCache {

	private List<Class<? extends Directive>> nullKeys;
	private Map<Class<? extends Directive>, Directive> valueMappings;

	public void put(Class<? extends Directive> key, Optional<? extends Directive> value) {
		if (valueMappings == null && value.isPresent()) {
			valueMappings = new IdentityHashMap<>(1);
		} else if (nullKeys == null && value.isEmpty()) {
			nullKeys = new ArrayList<>(4);
		}
		
		addValueToMap(key, value);
	}

	public <T extends Directive> Optional<T> computeIfAbsent(
		Class<T> directiveClass, Function<Class<T>, Optional<T>> value
	) {
		Optional<T> result = get(directiveClass);
		if (result != null) {
			return result;
		}

		result = value.apply(directiveClass);
		put(directiveClass, result);
		return result;
	}

	@SuppressWarnings("unchecked")
	private <T extends Directive> Optional<T> get(Class<T> directiveClass) {
		if (nullKeys != null && nullKeys.contains(directiveClass)) {
			return Optional.empty();
		} else if (valueMappings != null) {
			Directive directive = valueMappings.get(directiveClass);
			if (directive == null) return null;
			return Optional.of((T) directive);
		} else {
			return null;
		}
	}

	private void addValueToMap(Class<? extends Directive> key, Optional<? extends Directive> value) {
		if (value.isPresent()) {
			if (nullKeys != null) nullKeys.remove(key);
			valueMappings.put(key, value.get());
		} else {
			nullKeys.add(key);
			if (valueMappings != null) valueMappings.remove(key);
		}
	}

}
