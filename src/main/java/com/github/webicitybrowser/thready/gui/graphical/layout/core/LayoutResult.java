package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface LayoutResult {

	ChildLayoutResult[] childLayoutResults();
	
	AbsoluteSize fitSize();
	
	public static LayoutResult create(ChildLayoutResult[] childLayoutResults, AbsoluteSize fitSize) {
		return new LayoutResult() {

			@Override
			public ChildLayoutResult[] childLayoutResults() {
				return childLayoutResults;
			}

			@Override
			public AbsoluteSize fitSize() {
				return fitSize;
			}
			
		};
	}
	
}
