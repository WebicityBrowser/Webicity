package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.appearence.BlockAppearence;
import everyos.engine.ribbon.ui.simple.layout.InlineBlockLayout;
import everyos.engine.ribbon.ui.simple.layout.Layout;

public class SimpleBlockComponentUI extends SimpleComponentUI {
	private final Layout layout;
	private final Appearence appearence;

	public SimpleBlockComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.layout = new InlineBlockLayout(c, this);
		this.appearence = new BlockAppearence();
	}
	
	@Override
	protected Layout getLayout() {
		return this.layout;
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
}
