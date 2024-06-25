package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;

public final class FlexContentJustification {
	
	private FlexContentJustification() {}

	public static void justifyContent(List<FlexLine> flexLines, FlexJustifyContent justifyContent, FlexDirection flexDirection) {
		for (FlexLine flexLine : flexLines) {
			justifyContent(flexLine, justifyContent, flexDirection);
		}
	}

	public static void justifyContent(FlexLine line, FlexJustifyContent justifyContent, FlexDirection flexDirection) {
		switch (justifyContent) {
			case CENTER:
				justifyContentFlexCenter(line, flexDirection);
				break;
			case FLEX_END:
				justifyContentFlexEnd(line, flexDirection);
				break;
			case FLEX_START:
				justifyContentFlexStart(line, flexDirection);
				break;
			case SPACE_AROUND:
				justifySpaceAround(line, flexDirection);
				break;
			case SPACE_BETWEEN:
				justifySpaceBetween(line, flexDirection);
				break;
			default:
				throw new IllegalArgumentException("Unknown justify content: " + justifyContent);
		}
	}

	private static void justifyContentFlexCenter(FlexLine line, FlexDirection flexDirection) {
		float usedSpace = line.getUsedMainSize();
		float maxSpace = line.getMainSize();

		float mainPosition = (maxSpace - usedSpace) / 2;
		for (FlexItem flexItem : line.getFlexItems()) {
			flexItem.setItemOffset(new FlexDimension(mainPosition, 0, flexDirection));
			mainPosition += flexItem.getMainSize();
		}
	}

	private static void justifyContentFlexEnd(FlexLine line, FlexDirection flexDirection) {
		float usedSpace = line.getUsedMainSize();
		float maxSpace = line.getMainSize();

		float mainPosition = Math.max(0, maxSpace - usedSpace);
		for (FlexItem flexItem : line.getFlexItems()) {
			flexItem.setItemOffset(new FlexDimension(mainPosition, 0, flexDirection));
			mainPosition += flexItem.getMainSize();
		}
	}

	private static void justifyContentFlexStart(FlexLine line, FlexDirection flexDirection) {
		float mainPosition = 0;
		for (FlexItem flexItem : line.getFlexItems()) {
			flexItem.setItemOffset(new FlexDimension(mainPosition, 0, flexDirection));
			mainPosition += flexItem.getMainSize();
		}
	}

	private static void justifySpaceAround(FlexLine line, FlexDirection flexDirection) {
		float usedSpace = line.getUsedMainSize();
		float maxSpace = line.getMainSize();

		float spaceAround = Math.max(0, maxSpace - usedSpace) / line.getFlexItems().size();

		float mainPosition = spaceAround / 2;
		for (FlexItem flexItem : line.getFlexItems()) {
			flexItem.setItemOffset(new FlexDimension(mainPosition, 0, flexDirection));
			mainPosition += flexItem.getMainSize() + spaceAround;
		}
		mainPosition -= spaceAround / 2;
	}

	private static void justifySpaceBetween(FlexLine line, FlexDirection flexDirection) {
		float usedSpace = line.getUsedMainSize();
		float maxSpace = line.getMainSize();

		float spaceBetween = Math.max(0, maxSpace - usedSpace) / (line.getFlexItems().size() - 1);

		float mainPosition = 0;
		for (FlexItem flexItem : line.getFlexItems()) {
			flexItem.setItemOffset(new FlexDimension(mainPosition, 0, flexDirection));
			mainPosition += flexItem.getMainSize() + spaceBetween;
		}
		mainPosition -= spaceBetween;
	}

}
