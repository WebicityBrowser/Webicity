package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.LineBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;

public class LineBoxTest {

	private LineBox lineBox;

	@BeforeEach
	public void setup() {
		lineBox = new LineBox(new HorizontalLineDimensionConverter());
	}

	@Test
	@DisplayName("Can fit small unit in large line box")
	public void canFitSmallUnitInLargeLineBox() {
		AbsoluteSize unitSize = new AbsoluteSize(10, 10);
		AbsoluteSize maxLineSize = new AbsoluteSize(100, 100);
		Assertions.assertTrue(lineBox.canFit(unitSize, maxLineSize));
	}
	
	@Test
	@DisplayName("Can not fit large unit in small line box")
	public void canNotFitLargeUnitInSmallLineBox() {
		AbsoluteSize unitSize = new AbsoluteSize(100, 100);
		AbsoluteSize maxLineSize = new AbsoluteSize(10, 10);
		Assertions.assertFalse(lineBox.canFit(unitSize, maxLineSize));
	}

	@Test
	@DisplayName("Can fit unit in line box with same size")
	public void canFitUnitInLineBoxWithSameSize() {
		AbsoluteSize unitSize = new AbsoluteSize(100, 100);
		AbsoluteSize maxLineSize = new AbsoluteSize(100, 100);
		Assertions.assertTrue(lineBox.canFit(unitSize, maxLineSize));
	}

	@Test
	@DisplayName("Final size of line box with one unit is the size of the unit")
	@SuppressWarnings("unchecked")
	public void finalSizeOfLineBoxWithOneUnitIsMatchingSize() {
		BoundRenderedUnit<RenderedUnit> unit = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit);
		Assertions.assertEquals(new AbsoluteSize(100, 100), lineBox.getSize());
	}

	@Test
	@DisplayName("Final width of line box with two units is the sum of the widths of the units")
	@SuppressWarnings("unchecked")
	public void finalWidthOfLineBoxWithTwoUnitsIsSumOfWidths() {
		BoundRenderedUnit<RenderedUnit> unit1 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit1.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit1.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		BoundRenderedUnit<RenderedUnit> unit2 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit2.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit2.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit2);
		Assertions.assertEquals(new AbsoluteSize(200, 100), lineBox.getSize());
	}
	
	@Test
	@DisplayName("Final height of line box with two units is the height of the largest unit")
	@SuppressWarnings("unchecked")
	public void finalHeightOfLineBoxWithTwoUnitsIsHeightOfLargestUnit() {
		BoundRenderedUnit<RenderedUnit> unit1 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit1.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit1.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		BoundRenderedUnit<RenderedUnit> unit2 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit2.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit2.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 200));
		lineBox.add(unit2);
		Assertions.assertEquals(200, lineBox.getSize().height());
	}

	@Test
	@DisplayName("Render results of two normal units are positioned correctly")
	@SuppressWarnings("unchecked")
	public void renderResultsOfTwoNormalUnitsPositionedCorrectly() {
		BoundRenderedUnit<RenderedUnit> unit1 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit1.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit1.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		BoundRenderedUnit<RenderedUnit> unit2 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit2.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit2.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit2);
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(0, 0));
		Assertions.assertEquals(2, results.size());
		Assertions.assertEquals(new AbsolutePosition(0, 0), results.get(0).relativeRect().position());
		Assertions.assertEquals(new AbsolutePosition(100, 0), results.get(1).relativeRect().position());
	}

	@Test
	@DisplayName("Offset line has offset layout results")
	@SuppressWarnings("unchecked")
	public void offsetLineHasOffsetLayoutResults() {
		BoundRenderedUnit<RenderedUnit> unit1 = Mockito.mock(BoundRenderedUnit.class);
		Mockito.when(unit1.getRaw()).thenReturn(Mockito.mock(RenderedUnit.class));
		Mockito.when(unit1.getRaw().preferredSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(100, 200));
		Assertions.assertEquals(1, results.size());
		Assertions.assertEquals(new AbsolutePosition(100, 200), results.get(0).relativeRect().position());
	}

}
