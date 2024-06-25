package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;

public class FlexLine {

	private final FlexDirection flexDirection;
	private final List<FlexItem> flexItems;

	private float mainSize = RelativeDimension.UNBOUNDED;
	private float crossSize = RelativeDimension.UNBOUNDED;

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

	public float getUsedMainSize() {
		float mainSize = 0;
		for (FlexItem flexItem : flexItems) {
			mainSize += flexItem.getMainSize();
		}

		return mainSize;
	}

	public float getCrossSize() {
		if (crossSize != RelativeDimension.UNBOUNDED) return crossSize;

		float crossSize = 0;
		for (FlexItem flexItem : flexItems) {
			crossSize = Math.max(crossSize, flexItem.getCrossSize());
		}

		return crossSize;
	}

	public void setCrossSize(float crossSize) {
		this.crossSize = crossSize;
    }

	public float getMainSize() {
		if (mainSize == RelativeDimension.UNBOUNDED) {
			mainSize = getUsedMainSize();
		}

		return mainSize;
	}

	public void setMainSize(float mainSize) {
		this.mainSize = mainSize;
	}

	private List<FlexItem> getReversedItems() {
		List<FlexItem> reversedItems = new ArrayList<>();
		for (int i = flexItems.size() - 1; i >= 0; i--) {
			reversedItems.add(flexItems.get(i));
		}

		return reversedItems;
	}

}
