package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.ui.UIManager;

public class SimpleUIManager {
	public static UIManager createUI() {
		UIManager manager = new UIManager();
		manager.put(Component.class, (c, p)->new SimpleComponentUI(c, p));
		manager.put(BlockComponent.class, (c, p)->new SimpleBlockComponentUI(c, p));
		manager.put(LabelComponent.class, (c, p)->new SimpleLabelComponentUI(c, p));
		manager.put(TextBoxComponent.class, (c, p)->new SimpleTextBoxComponentUI(c, p));
		manager.put(BreakComponent.class, (c, p)->new SimpleBreakComponentUI(c, p)	);
		return manager;
	}
}
