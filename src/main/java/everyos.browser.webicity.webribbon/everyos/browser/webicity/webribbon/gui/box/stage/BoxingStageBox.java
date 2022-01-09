package everyos.browser.webicity.webribbon.gui.box.stage;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.box.Box;

public interface BoxingStageBox extends Box {

	// TODO: Perhaps, instead of MultiBoxes, there should be a method to convert between box types
	void integrate(MultiBox[] boxes);
	void add(MultiBox box);
	void finish();
	
	//TODO: Do boxes own content?
	void setContent(Content content);
	
}
