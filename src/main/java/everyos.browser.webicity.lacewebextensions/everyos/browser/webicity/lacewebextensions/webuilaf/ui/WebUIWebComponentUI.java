package everyos.browser.webicity.lacewebextensions.webuilaf.ui;

import com.github.webicity.lace.basics.component.directive.LayoutManagerDirective;
import com.github.webicity.lace.core.accessibility.AccessibilityDescriptor;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.layout.LayoutManager;
import com.github.webicity.lace.core.layout.LayoutManagerUI;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.box.BoxStageContext;

import everyos.browser.webicity.lacewebextensions.basics.layout.flow.FlowLayoutManager;
import everyos.browser.webicity.lacewebextensions.core.pipeline.box.BlockLevelBoxStageBox;
import everyos.browser.webicity.lacewebextensions.webuilaf.content.WebUIWebComponentContent;

public class WebUIWebComponentUI extends WebUIComponentUIBase {

	public WebUIWebComponentUI(Component component, ComponentUI parentUI) {
		super(component, parentUI);
	}

	@Override
	public void box(BoxStageBox parent, BoxStageContext context) {
		LayoutManagerUI layoutManagerUI = getLayoutManager().createUI(this);
		
		BoxStageBox box = createBox(layoutManagerUI);
		
		layoutManagerUI.box(box, context);
		
		box.finish(parent);
	}
	
	@Override
	public AccessibilityDescriptor getAccessibilityDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected BoxStageBox createBox(LayoutManagerUI layoutManagerUI) {
		return new BlockLevelBoxStageBox(new WebUIWebComponentContent(layoutManagerUI), getDirectiveManager());
	}
	
	private LayoutManager getLayoutManager() {
		 return getComponent()
			.getRawDirective(LayoutManagerDirective.class)
			.map(directive -> directive.getLayoutManager())
			.orElseGet(() -> new FlowLayoutManager());
	}

}
