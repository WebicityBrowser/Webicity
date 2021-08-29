package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebDocumentComponent;
import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class WebComponentWrapper extends BlockComponent {
	
	private WebDocumentComponent webComponent;
	private Pallete pallete;

	public WebComponentWrapper ui(WebDocumentComponent component) {
		this.webComponent = component;
		invalidate(InvalidationLevel.RENDER);
		
		return this;
	}
	
	public WebComponentWrapper pallete(Pallete pallete) {
		this.pallete = pallete;
		invalidate(InvalidationLevel.PAINT);
		
		return this;
	}
	
	public WebDocumentComponent getUI() {
		return this.webComponent;
	}

	public Pallete getPallete() {
		return this.pallete;
	}
	
}
