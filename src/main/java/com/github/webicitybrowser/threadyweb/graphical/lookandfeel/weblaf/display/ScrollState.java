package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

public class ScrollState {
	
	private final ScrollContext scrollContext;

	private float scrollStartPosition = -1;
	private float initialPageScrollPosition = -1;

	public ScrollState(ScrollContext scrollContext) {
		this.scrollContext = scrollContext;
	}

	public void startScroll(float y) {
		this.scrollStartPosition = y;
		this.initialPageScrollPosition = scrollContext.scrollPosition().y();
	}

	public void endScroll() {
		this.scrollStartPosition = -1;
	}
	
	public float scrollStartPosition() {
		return scrollStartPosition;
	}
	
	public float initialPageScrollPosition() {
		return initialPageScrollPosition;
	}

	public boolean isScrolling() {
		return scrollStartPosition != -1;
	}

}
