package everyos.browser.webicity.webribbon.ui.webui.ui;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.spec.jcss.cssom.CSSOMUtil;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssvalue.display.OuterDisplayType;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.BlockDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.ContentsDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.DisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.InlineDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.NoneDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.ui.content.ActivationBehaviorContent;

public class WebUIWebComponentUI extends WebUIWebComponentUIBase {
	
	private DisplayMode outerDisplay;
	private DisplayMode innerDisplay;

	public WebUIWebComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager) {
		CSSOMNode[] matchingNodes = CSSOMUtil.getMatchingNodes(getComponent().getNode(), cssomNode);
		ApplicablePropertyMap properties = CSSOMUtil.calculateProperties(matchingNodes, parent);
		
		calculateDisplayProperties(properties, cssomNode, manager);
		outerDisplay.recalculateCSSOM(cssomNode, properties, manager);
	}
	
	@Override
	public void box(BoxingStageBox parent, WebBoxContext context) {
		outerDisplay.box(parent, context);
	}
	
	protected Content getContentOverride() {
		return new ActivationBehaviorContent(innerDisplay.getContent(), getComponent());
	}
	
	private void calculateDisplayProperties(ApplicablePropertyMap properties, CSSOMNode cssomNode, WebUIManager manager) {
		this.innerDisplay = createInnerDisplay();
		
		OuterDisplayType outerDisplayType = ((DisplayProperty) properties.getPropertyByName(PropertyName.DISPLAY)).getOuterDisplayType();
		this.outerDisplay = createOuterDisplay(outerDisplayType, innerDisplay);
	}
	
	private DisplayMode createInnerDisplay() {
		return new FlowDisplayMode(getComponent(), this);
	}

	private DisplayMode createOuterDisplay(OuterDisplayType outerDisplayType, DisplayMode innerDisplay) {
		switch (outerDisplayType) {
			case INLINE:
				return new InlineDisplayMode(innerDisplay, getContentOverride());
				
			case CONTENTS:
				return new ContentsDisplayMode(getComponent(), this);
				
			case NONE:
				return new NoneDisplayMode();
		
			case BLOCK:
			default:
				return new BlockDisplayMode(this, innerDisplay, getContentOverride());
		}
		
	}
	
}
