package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.components.component.BlockComponent;
import everyos.engine.ribbon.components.component.BreakComponent;
import everyos.engine.ribbon.components.component.LabelComponent;
import everyos.engine.ribbon.components.component.TextBoxComponent;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.ui.UIManager;

public class SimpleUIManager {
	
	public static UIManager createUI() {
		UIManager manager = new UIManager();
		manager.put(Component.class, SimpleComponentUI::new);
		manager.put(BlockComponent.class, SimpleBlockComponentUI::new);
		manager.put(LabelComponent.class, SimpleLabelComponentUI::new);
		manager.put(TextBoxComponent.class, SimpleTextBoxComponentUI::new);
		manager.put(BreakComponent.class, SimpleBreakComponentUI::new);
		
		return manager;
	}
	
}
