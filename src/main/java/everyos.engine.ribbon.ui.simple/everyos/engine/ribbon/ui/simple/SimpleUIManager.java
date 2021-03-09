package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.BufferedComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.helper.ReflectiveFactory;

public class SimpleUIManager {
	public static UIManager createUI() {
		UIManager manager = new UIManager();
		manager.put(Component.class, new ReflectiveFactory(SimpleComponentUI.class));
		manager.put(BlockComponent.class, new ReflectiveFactory(SimpleBlockComponentUI.class));
		manager.put(BufferedComponent.class, new ReflectiveFactory(SimpleBufferedComponentUI.class));
		manager.put(LabelComponent.class, new ReflectiveFactory(SimpleLabelComponentUI.class));
		manager.put(TextBoxComponent.class, new ReflectiveFactory(SimpleTextBoxComponentUI.class));
		manager.put(BreakComponent.class, new ReflectiveFactory(SimpleBreakComponentUI.class));
		return manager;
	}
}
