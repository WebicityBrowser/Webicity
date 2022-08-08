package everyos.browser.webicity.lacewebextensions.webuilaf.ui;

import com.github.webicity.lace.basics.component.directive.BasicDirectiveManager;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.component.directive.DirectiveManager;
import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.imputils.laf.ComponentUIBase;

public abstract class WebUIComponentUIBase extends ComponentUIBase {

	public WebUIComponentUIBase(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}

	@Override
	protected DirectiveManager createDirectiveManager() {
		return new BasicDirectiveManager(getComponent());
	}
	
}
