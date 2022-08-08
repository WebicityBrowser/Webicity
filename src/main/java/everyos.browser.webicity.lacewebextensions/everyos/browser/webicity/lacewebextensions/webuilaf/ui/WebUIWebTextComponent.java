package everyos.browser.webicity.lacewebextensions.webuilaf.ui;

import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.layout.LayoutManagerUI;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;

import everyos.browser.webicity.lacewebextensions.core.pipeline.box.InlineLevelBoxStageBox;
import everyos.browser.webicity.lacewebextensions.webuilaf.content.WebUIWebTextComponentContent;

public class WebUIWebTextComponent extends WebUIWebComponentUI {

	public WebUIWebTextComponent(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}

	@Override
	protected BoxStageBox createBox(LayoutManagerUI layoutManagerUI) {
		return new InlineLevelBoxStageBox(new WebUIWebTextComponentContent(), getDirectiveManager());
	}

}
