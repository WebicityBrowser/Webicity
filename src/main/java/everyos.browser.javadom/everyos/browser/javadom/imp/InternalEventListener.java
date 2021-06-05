package everyos.browser.javadom.imp;

import java.util.Objects;

import everyos.browser.javadom.intf.EventListener;

@SuppressWarnings("unused")
public class InternalEventListener {
	private String type;
	private EventListener callback;
	private boolean capture;
	private boolean passive;
	private boolean once;
	private boolean removed;
	
	public InternalEventListener(String type, EventListener callback, boolean capture, boolean passive, boolean once) {
		this.type = type;
		this.callback = callback;
		this.capture = capture;
		this.passive = passive;
		this.once = once;
	}
	
	public EventListener getCallback() {
		return callback;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof InternalEventListener)) {
			return false;
		}
		InternalEventListener listener = (InternalEventListener) object;
		
		return
			listener.getType().equals(getType()) &&
			listener.getCallback().equals(getCallback()) &&
			listener.getCapture() == getCapture();
	}
	@Override
	public int hashCode() {
		return Objects.hash(getType(), getCallback(), getCapture());
	}
	
	private String getType() {
		return type;
	}

	private boolean getCapture() {
		return capture;
	}
}
