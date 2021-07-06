package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.appearence.DefaultAppearence;

public class SimpleBreakComponentUI extends SimpleComponentUI {
	private Appearence appearence;
	
	public SimpleBreakComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new BreakAppearence();
	}
	
	
	@Override
	public Appearence getAppearence() {
		return this.appearence;
	}
	
	
	private class BreakAppearence extends DefaultAppearence {
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
			sizepos.nextLine();
		}
	}
}
