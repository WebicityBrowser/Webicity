package everyos.browser.webicity.webribbon.gui.box;

public interface MutableBox extends Box {
	
	void integrate(Box[] boxes);
	void add(Box box);
	void finish();
	
}
