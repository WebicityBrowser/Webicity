package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;

public class FlexLine {

	private final FlexDirection flexDirection;
	private final List<FlexItem> flexItems;

	private float crossSize;

	public FlexLine(FlexDirection flexDirection) {
		this.flexDirection = flexDirection;
		this.flexItems = new ArrayList<>();
	}

	public FlexDirection getFlexDirection() {
		return flexDirection;
	}

	public void addFlexItem(FlexItem flexItem) {
		flexItems.add(flexItem);
	}

	public List<FlexItem> getFlexItems() {
		return switch (flexDirection) {
			case ROW, COLUMN -> flexItems;
			case ROW_REVERSE, COLUMN_REVERSE -> getReversedItems();
		};
	}

	public float getMainSize() {
		float mainSize = 0;
		for (FlexItem flexItem : flexItems) {
			mainSize += flexItem.getMainSize();
		}

		return mainSize;
	}

	public float getCrossSize() {
		if (crossSize != 0) return crossSize;

		float crossSize = 0;
		for (FlexItem flexItem : flexItems) {
			crossSize = Math.max(crossSize, flexItem.getCrossSize());
		}

		return crossSize;
	}

	public void setCrossSize(float crossSize) {
		this.crossSize = crossSize;
    }

	private List<FlexItem> getReversedItems() {
		List<FlexItem> reversedItems = new ArrayList<>();
		for (int i = flexItems.size() - 1; i >= 0; i--) {
			reversedItems.add(flexItems.get(i));
		}

		return reversedItems;
	}

}
