package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp;

import java.util.List;

import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.laf.LookAndFeel;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.box.BoxStageContext;
import com.github.webicity.lace.imputils.util.ChildrenUICache;

import everyos.browser.webicity.lacewebextensions.basics.layout.common.UIActionHandler;
import everyos.browser.webicity.lacewebextensions.core.component.WebComponent;

public class FlowLayoutBoxStage {
	
	private final ComponentUI parentComponentUI;
	private final ChildrenUICache computedChildUIHelper;

	public FlowLayoutBoxStage(ComponentUI parentComponentUI) {
		this.parentComponentUI = parentComponentUI;
		this.computedChildUIHelper = new ChildrenUICache(parentComponentUI);
	}
	
	public void box(BoxStageBox parent, BoxStageContext context) {
		computeChildUIs(context.getLookAndFeel());
		createChildUIBoxes(parent, context);
	}
	
	private List<Component> getChildren() {
		return parentComponentUI
			.getComponent()
			.<WebComponent>casted()
			.getChildren();
	}

	private void computeChildUIs(LookAndFeel lookAndFeel) {
		computedChildUIHelper.setLookAndFeel(lookAndFeel);
		computedChildUIHelper.setChildren(getChildren());
	}

	private void createChildUIBoxes(BoxStageBox parent, BoxStageContext context) {
		for (ComponentUI childUI: computedChildUIHelper.getComputedUIs()) {
			UIActionHandler.box(childUI, parent, context);
		}
	}
	
}
