package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public class DocumentDirectivePoolCache {

	private List<Class<? extends Directive>> nullKeys;
	private Map<Class<? extends Directive>, Directive> valueMappings;

	public void put(Class<? extends Directive> key, Optional<Directive> value) {
		if (valueMappings == null && value.isPresent()) {
			valueMappings = new HashMap<>(1);
		} else if (nullKeys == null && value.isEmpty()) {
			nullKeys = new ArrayList<>(4);
		}
		
		addValueToMap(key, value);
	}

	public Optional<Directive> computeIfAbsent(
		Class<? extends Directive> directiveClass, Function<Class<? extends Directive>, Optional<Directive>> value
	) {
		Optional<Directive> result = get(directiveClass);
		if (result != null) {
			return result;
		}

		result = value.apply(directiveClass);
		put(directiveClass, result);
		return result;
	}

	private Optional<Directive> get(Class<? extends Directive> directiveClass) {
		if (nullKeys != null && nullKeys.contains(directiveClass)) {
			return Optional.empty();
		} else if (valueMappings != null) {
			Directive directive = valueMappings.get(directiveClass);
			if (directive == null) return null;
			return Optional.of(directive);
		} else {
			return null;
		}
	}

	private void addValueToMap(Class<? extends Directive> key, Optional<Directive> value) {
		if (value.isPresent()) {
			if (nullKeys != null) nullKeys.remove(key);
			valueMappings.put(key, value.get());
		} else {
			nullKeys.add(key);
			if (valueMappings != null) valueMappings.remove(key);
		}
	}

}
