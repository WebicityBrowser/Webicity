package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.test;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;

public class TestCSSOMParticipant<T> implements CSSOMParticipant<T> {

	private final T value;
	private final CSSOMParticipant<T> parent;
	private final List<CSSOMParticipant<T>> children;

	public TestCSSOMParticipant(T value, CSSOMParticipant<T> parent) {
		this.value = value;
		this.parent = parent;
		this.children = new ArrayList<>();
	}

	@Override
	public T getValue() {
		return this.value;
	}
	
	@Override
	public CSSOMParticipant<T> getParent() {
		return this.parent;
	}

	@Override
	public List<CSSOMParticipant<T>> getChildren() {
		return List.copyOf(this.children);
	}

	public void addChild(CSSOMParticipant<T> child) {
		children.add(child);
	}

}
