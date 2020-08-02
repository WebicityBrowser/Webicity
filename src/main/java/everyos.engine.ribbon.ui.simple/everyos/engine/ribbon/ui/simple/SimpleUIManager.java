package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.BufferedComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;

public class SimpleUIManager {
	public static UIManager<GUIComponentUI> createUI() {
		UIManager<GUIComponentUI> manager = new UIManager<GUIComponentUI>();
		manager.put(Component.class, new SimpleComponentUI());
		manager.put(BlockComponent.class, new SimpleBlockComponentUI());
		manager.put(BufferedComponent.class, new SimpleBufferedComponentUI());
		manager.put(LabelComponent.class, new SimpleLabelComponentUI());
		manager.put(TextBoxComponent.class, new SimpleTextBoxComponentUI());
		manager.put(BreakComponent.class, new SimpleBreakComponentUI());
		return manager;
	}
}
