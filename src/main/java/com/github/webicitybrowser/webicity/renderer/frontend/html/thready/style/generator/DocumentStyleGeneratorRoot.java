package com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.generator;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom.CSSOMNode;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Supplier<CSSOMNode> cssomRootSupplier;

	public DocumentStyleGeneratorRoot(Supplier<CSSOMNode> cssomRootSupplier) {
		this.cssomRootSupplier = cssomRootSupplier;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		CSSOMNode cssomRoot = cssomRootSupplier.get();
		return new DocumentStyleGenerator(new CSSOMNode[] { cssomRoot });
	}

}
