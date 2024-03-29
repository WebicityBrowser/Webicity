package com.github.webicitybrowser.webicitybrowser.ui.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.ui.Frame;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;
import com.github.webicitybrowser.webicitybrowser.ui.event.TabMutationEventListener;

public class TabImp implements Tab {
	
	private final Frame frame;

	private final List<TabMutationEventListener> mutationListeners = new ArrayList<>();

	public TabImp(BrowserInstance browserInstance) {
		this.frame = browserInstance.getRenderingEngine().createFrame();
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
	public Frame getFrame() {
		return this.frame;
	}

	@Override
	public String getName() {
		String frameName = frame.getName();
		if (frameName == null || frameName.isEmpty()) {
			return frame.getURL().toString();
		}
		return frame.getName();
	}

	@Override
	public URL getURL() {
		return frame.getURL();
	}

	@Override
	public void navigate(URL url) {
		frame.navigate(url);
		for (TabMutationEventListener mutationListener : mutationListeners) {
			mutationListener.onNavigate(this, url);
		}
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
		mutationListeners.add(mutationListener);
		if (sync) {
			mutationListener.onNavigate(this, getURL());
		}
	}

	@Override
	public void removeTabMutationEventListener(TabMutationEventListener mutationListener) {
		mutationListeners.remove(mutationListener);
	}

}
