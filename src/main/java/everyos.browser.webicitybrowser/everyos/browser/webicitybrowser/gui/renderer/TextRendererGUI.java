package everyos.browser.webicitybrowser.gui.renderer;

import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;
import everyos.browser.webicitybrowser.gui.colors.Colors;
import everyos.engine.ribbon.components.component.BlockComponent;
import everyos.engine.ribbon.components.component.LabelComponent;
import everyos.engine.ribbon.core.graphics.Component;

public class TextRendererGUI {
	
	private final PlainTextRenderer renderer;
	private final Component displayPane;

	public TextRendererGUI(PlainTextRenderer renderer, Colors colors) {
		this.renderer = renderer;
		
		this.displayPane = new BlockComponent();
	}
	
	public void start() {
		renderer.addReadyHook(()->{
			LabelComponent innerPane = new LabelComponent();
			
			System.out.println(renderer.getContent());
			innerPane.text(renderer.getContent());
			
			displayPane.children(new Component[] { innerPane });
		});
	}
	
	public void cleanup() {
		
	}

	public Component getDisplayPane() {
		return displayPane;
	}
	
}
