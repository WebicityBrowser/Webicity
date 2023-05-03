package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Supplier<CSSOMNode[]> cssomRootsSupplier;

	public DocumentStyleGeneratorRoot(Supplier<CSSOMNode[]> cssomRootsSupplier) {
		this.cssomRootsSupplier = cssomRootsSupplier;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		CSSOMNode[] cssomRoots = cssomRootsSupplier.get();
		return new DocumentStyleGenerator(cssomRoots);
	}

}
