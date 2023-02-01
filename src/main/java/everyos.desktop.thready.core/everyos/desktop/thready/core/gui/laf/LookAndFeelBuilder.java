package everyos.desktop.thready.core.gui.laf;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.imp.LookAndFeelBuilderImp;

public interface LookAndFeelBuilder {

	<T extends Component> void registerComponentUI(Class<T> componentClass, ComponentUIFactory provider);
	
	LookAndFeel build();

	public static LookAndFeelBuilder create() {
		return new LookAndFeelBuilderImp();
	}
	
}
