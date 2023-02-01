package everyos.desktop.thready.laf.simple;

import everyos.desktop.thready.basic.component.ContainerComponent;
import everyos.desktop.thready.core.gui.laf.LookAndFeelBuilder;
import everyos.desktop.thready.laf.simple.component.SimpleContainerComponentUI;

public class SimpleLookAndFeel {

	public static void installTo(LookAndFeelBuilder builder) {
		builder.registerComponentUI(ContainerComponent.class, SimpleContainerComponentUI::new);
	}

}
