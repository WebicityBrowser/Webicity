package everyos.browser.webicity.webribbon.core.ui;

import java.util.function.Consumer;

import everyos.browser.webicity.webribbon.gui.box.MutableBlockLevelBox;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public interface WebWindowUI extends WebComponentUI {

	MutableBlockLevelBox createBox();
	void onInvalidation(Consumer<InvalidationLevel> func);

}
