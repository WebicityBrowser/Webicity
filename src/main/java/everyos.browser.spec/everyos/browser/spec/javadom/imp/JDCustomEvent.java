package everyos.browser.spec.javadom.imp;

import everyos.browser.spec.javadom.intf.CustomEvent;

public class JDCustomEvent extends JDEvent implements CustomEvent {
	private Object detail;

	@Override
	public Object getDetail() {
		return detail;
	}

	@Override
	public void initCustomEvent(String type, boolean bubbles, boolean cancelable, Object detail) {
		if (getDispatch()) return;
		initialize(type, bubbles, cancelable);
		this.detail = detail;
	}
}
