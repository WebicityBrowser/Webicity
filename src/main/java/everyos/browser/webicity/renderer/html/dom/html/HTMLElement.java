package everyos.browser.webicity.renderer.html.dom.html;

import everyos.browser.webicity.renderer.html.dom.Element;

public interface HTMLElement extends Element {
	//[HTMLConstructor] constructor();

	// metadata attributes
	String getTitle();
	void setTitle(String title);
	String getLang();
	void setLang(String lang);
	boolean getTranslate();
	void setTranslate(boolean translate);
	String getDir();
	void setDir(String dir);

	// user interaction
	boolean getHidden();
	void setHidden(boolean hidden);
	void click();
	String getAccessKey();
	void setAccessKey(String key);
	String getAccessKeyLabel();
	boolean getDraggable();
	void setDraggable(boolean draggable);
	boolean getSpellcheck();
	void setSpellcheck(boolean spellcheck);
	String getAutocapitalize();
	void setAutocapitalize(String autocapitalize);

	String getInnerText();
	void setInnerText();

	ElementInternals attachInternals();
}
