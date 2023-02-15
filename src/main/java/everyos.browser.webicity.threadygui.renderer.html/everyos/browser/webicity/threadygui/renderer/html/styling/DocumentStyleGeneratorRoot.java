package everyos.browser.webicity.threadygui.renderer.html.styling;

import java.util.function.Supplier;

import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMNode;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.directive.style.StyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.web.spec.css.rule.CSSStyleSheet;

public class DocumentStyleGeneratorRoot implements StyleGeneratorRoot {
	
	private final Supplier<CSSStyleSheet[]> stylesheetSupplier;

	public DocumentStyleGeneratorRoot(Supplier<CSSStyleSheet[]> stylesheetSupplier) {
		this.stylesheetSupplier = stylesheetSupplier;
	}

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		CSSStyleSheet[] stylesheets = stylesheetSupplier.get();
		CSSOMNode cssomRoot = CSSOMGenerator.generateCSSOM(stylesheets);
		
		return new DocumentStyleGenerator(new CSSOMNode[] { cssomRoot });
	}

	@Override
	public void onMassChange(Runnable listener) {
		// TODO Auto-generated method stub
		
	}

}
