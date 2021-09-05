package everyos.browser.spec.jcss.intf;

public interface StyleSheet {
	String getType();
	String getHref();
	Object getOwnerNode();
	CSSStyleSheet getParentStyleSheet();
	String getTitle();
	MediaList getMedia();
	boolean getDisabled();
	boolean setDisabled(boolean disabled);
}
