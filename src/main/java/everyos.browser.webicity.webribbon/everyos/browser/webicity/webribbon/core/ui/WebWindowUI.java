package everyos.browser.webicity.webribbon.core.ui;

import java.util.function.Consumer;

import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public interface WebWindowUI extends WebComponentUI {

	MultiBox createBox();
	void onInvalidation(Consumer<InvalidationLevel> func);

}
