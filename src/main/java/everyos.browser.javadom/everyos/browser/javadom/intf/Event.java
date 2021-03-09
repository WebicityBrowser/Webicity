package everyos.browser.javadom.intf;

import java.util.List;

public interface Event {
	String getType();
	EventTarget getTarget();
	EventTarget getSrcElement();
	EventTarget getCurrentTarget();
	List<EventTarget> composedPath(); //TODO: Switch to Infra List
	
	//TODO: Constants are hard
	short getEventPhase();
	
	void stopPropogation();
	boolean getCancelBubble();
	void setCancelBubble(boolean v);
	void stopImmediatePropogation();
	
	boolean getBubbles();
	boolean getCancelable();
	boolean getReturnValue();
	void setReturnValue(boolean v);
	void preventDefault();
	boolean getDefaultPrevented();
	boolean getComposed();
	boolean getIsTrusted();
	double getTimeStamp();
	
	void initEvent(String type, boolean bubbles, boolean cancelable);
}
