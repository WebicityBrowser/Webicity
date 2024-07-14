package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base;

import java.util.Optional;
import java.util.WeakHashMap;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.style.GenericStyleReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleDefinition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleReference;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class GenericComponentUI implements ComponentUI {

	private final Component component;
	private final ComponentUI parent;
	private final UIDisplay<?, ?, ?> display;

	// TODO: There might be some cases were there is no style reference anymore but we still need to invalidate?
	private final WeakHashMap<Class<?>, GenericStyleReference<?>> styleReferences = new WeakHashMap<>(4);

	private LookAndFeel lookAndFeel;
	private DirectivePool styleDirectives;

	public GenericComponentUI(Component component, ComponentUI parent, UIDisplay<?, ?, ?> display) {
		this.component = component;
		this.parent = parent;
		this.display = display;
	}

	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public UIDisplay<?, ?, ?> getRootDisplay() {
		return display;
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Directive> StyleReference<T> getStyleReference(Class<T> directiveClass) {
		GenericStyleReference<T> styleReference = (GenericStyleReference<T>) styleReferences.get(directiveClass);
		if (styleReference == null) {
			styleReference = new GenericStyleReference<>(directiveClass);
			styleReferences.put(directiveClass, styleReference);
			updateStyleReference(styleReference);
		}

		return styleReference;
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		this.lookAndFeel = styleContext.lookAndFeel();
		this.styleDirectives = styleDirectives;
		for (GenericStyleReference<?> styleReference : styleReferences.values()) {
			updateStyleReference(styleReference);
		}
		invalidate(InvalidationLevel.BOX);
	}

	@Override
	@Deprecated
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	private <T extends Directive> void updateStyleReference(GenericStyleReference<T> styleReference) {
		StyleDefinition<T> styleDefinition = lookAndFeel.getStyleDefinition(styleReference.getDirectiveClass());
		Optional<T> maybeDirective = styleDefinition.inherited() ?
			styleDirectives.inheritDirectiveOrEmpty(styleReference.getDirectiveClass()) :
			styleDirectives.getDirectiveOrEmpty(styleReference.getDirectiveClass());
		T directive = maybeDirective.orElse(styleDefinition.defaultValue());

		styleReference.set(directive);
	}

}
