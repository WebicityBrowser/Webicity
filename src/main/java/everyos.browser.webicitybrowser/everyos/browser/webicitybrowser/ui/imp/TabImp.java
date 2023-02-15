package everyos.browser.webicitybrowser.ui.imp;

import everyos.browser.webicity.core.ui.Frame;
import everyos.browser.webicity.core.ui.imp.FrameImp;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.web.spec.uri.URL;

public class TabImp implements Tab {
	
	private final Frame frame;

	public TabImp() {
		this.frame = new FrameImp();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return frame.getName();
	}

	@Override
	public Frame getFrame() {
		return this.frame;
	}

	@Override
	public URL getURL() {
		return frame.getURL();
	}

	@Override
	public void navigate(URL url) {
		frame.navigate(url);
	}

	@Override
	public void reload() {
		frame.reload();
	}

	@Override
	public void back() {
		frame.back();
	}

	@Override
	public void forward() {
		frame.forward();
	}

	@Override
	public void addTabMutationEventListener(TabMutationEventListener mutationListener, boolean sync) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTabMutationEventListener(TabMutationEventListener mutationListener) {
		// TODO Auto-generated method stub

	}

}
