package everyos.engine.ribbon.graphics.ui.simple;

import everyos.engine.ribbon.graphics.component.BlockComponent;
import everyos.engine.ribbon.graphics.component.BreakComponent;
import everyos.engine.ribbon.graphics.component.BufferedComponent;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.component.ImageComponent;
import everyos.engine.ribbon.graphics.component.LabelComponent;
import everyos.engine.ribbon.graphics.component.TextBoxComponent;
import everyos.engine.ribbon.graphics.ui.UIManager;
import everyos.engine.ribbonawt.RibbonAWTWindowComponent;
import everyos.engine.ribbonawt.RibbonAWTWindowComponentUI;

public class SimpleUIManager {
	public static UIManager createUI() {
		UIManager manager = new UIManager();
		manager.put(Component.class, new SimpleComponentUI());
		manager.put(BlockComponent.class, new SimpleBlockComponentUI());
		manager.put(BufferedComponent.class, new SimpleBufferedComponentUI());
		manager.put(RibbonAWTWindowComponent.class, new RibbonAWTWindowComponentUI());
		manager.put(LabelComponent.class, new SimpleLabelComponentUI());
		manager.put(TextBoxComponent.class, new SimpleTextBoxComponentUI());
		manager.put(BreakComponent.class, new SimpleBreakComponentUI());
		manager.put(ImageComponent.class, new SimpleImageComponentUI());
		return manager;
	}
}
