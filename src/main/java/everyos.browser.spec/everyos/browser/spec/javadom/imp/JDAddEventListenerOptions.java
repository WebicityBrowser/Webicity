package everyos.browser.spec.javadom.imp;

import everyos.browser.spec.javadom.intf.AddEventListenerOptions;

public class JDAddEventListenerOptions implements AddEventListenerOptions {

	@Override
	public boolean getCapture() {
		return false;
	}

	@Override
	public boolean getPassive() {
		return false;
	}

	@Override
	public boolean getOnce() {
		return false;
	}
}
