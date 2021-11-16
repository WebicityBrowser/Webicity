package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;

public abstract class MutableBoxBase extends BoxBase implements MutableBox {

	@Override
	public void integrate(Box[] boxes) {
		for (Box box: boxes) {
			add(box);
		}
	}
	
}
