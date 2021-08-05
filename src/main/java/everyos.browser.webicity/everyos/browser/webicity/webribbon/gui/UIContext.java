package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;

public interface UIContext {
	WebUIManager getManager();
	void addTopLevelUIBox(UIBox box);
	Pallete getPallete();
}
