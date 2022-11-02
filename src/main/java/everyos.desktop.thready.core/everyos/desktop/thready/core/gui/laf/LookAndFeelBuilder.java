package everyos.desktop.thready.core.gui.laf;

import java.util.function.Function;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public interface LookAndFeelBuilder {

	<T extends Component> void registerComponentUI(Class<T> componentClass, Function<T, ComponentUI> provider);
	
	LookAndFeel build();
	
}
