package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;

public class DocumentContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	
	private DirectivePool styleDirectives;
	private Context childComponentContext;

	public DocumentContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return this.display;
	}
	
	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}

	@Override
	public List<Context> children() {
		return childComponentContext != null ?
			List.of(childComponentContext) :
			List.of();
	}

	public Optional<Context> getVisibleChildContext() {
		return Optional.ofNullable(childComponentContext);
    }
	
	public DocumentComponent component() {
		return (DocumentComponent) componentUI.getComponent();
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		this.styleDirectives = styleDirectives;
		
		updateChildComponentUI(styleContext);
	}

	private void updateChildComponentUI(StyleContext styleContext) {
		// TODO: Only make a new context if it would differ from the current one
		DocumentComponent documentComponent = (DocumentComponent) componentUI.getComponent();
		childComponentContext = documentComponent
			.getVisibleChild()
			.map(child -> styleContext.lookAndFeel().createUIFor(child, componentUI))
			.map(ui -> ui.getRootDisplay().createContext(ui))
			.orElse(null);
	}
	
}
