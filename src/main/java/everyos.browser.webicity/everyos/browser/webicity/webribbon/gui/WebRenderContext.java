package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.ui.WebUIManager;

public interface WebRenderContext {
	WebUIManager getManager();
	void addTopLevelUIBox(UIBox box);
	//Renderer getRenderer();
}
