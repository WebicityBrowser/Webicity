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
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.LineBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;

public class LineBoxTest {

	private LineBox lineBox;

	@BeforeEach
	public void setup() {
		lineBox = new LineBox(
			new HorizontalLineDimensionConverter(),
			directives -> new BuildableRenderedUnitImp(null, directives));
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
	public void finalSizeOfLineBoxWithOneUnitIsMatchingSize() {
		RenderedUnit unit = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit);
		Assertions.assertEquals(new AbsoluteSize(100, 100), lineBox.getSize());
	}

	@Test
	@DisplayName("Final width of line box with two units is the sum of the widths of the units")
	public void finalWidthOfLineBoxWithTwoUnitsIsSumOfWidths() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		RenderedUnit unit2 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit2.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit2);
		Assertions.assertEquals(new AbsoluteSize(200, 100), lineBox.getSize());
	}
	
	@Test
	@DisplayName("Final height of line box with two units is the height of the largest unit")
	public void finalHeightOfLineBoxWithTwoUnitsIsHeightOfLargestUnit() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		RenderedUnit unit2 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit2.fitSize()).thenReturn(new AbsoluteSize(100, 200));
		lineBox.add(unit2);
		Assertions.assertEquals(200, lineBox.getSize().height());
	}

	@Test
	@DisplayName("Render results of two normal units are positioned correctly")
	public void renderResultsOfTwoNormalUnitsPositionedCorrectly() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		RenderedUnit unit2 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit2.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit2);
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(0, 0));
		Assertions.assertEquals(2, results.size());
		Assertions.assertEquals(new AbsolutePosition(0, 0), results.get(0).relativeRect().position());
		Assertions.assertEquals(new AbsolutePosition(100, 0), results.get(1).relativeRect().position());
	}

	@Test
	@DisplayName("Offset line has offset layout results")
	public void offsetLineHasOffsetLayoutResults() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.add(unit1);
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(100, 200));
		Assertions.assertEquals(1, results.size());
		Assertions.assertEquals(new AbsolutePosition(100, 200), results.get(0).relativeRect().position());
	}

	@Test
	@DisplayName("Can mark nested unit start")
	public void canMarkNestedUnitStart() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.addMarker(new UnitEnterMarker(true, null));
		lineBox.add(unit1);
		lineBox.addMarker(new UnitExitMarker(null));
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(0, 0));
		Assertions.assertEquals(1, results.size());
		ChildLayoutResult outerResult = results.get(0);
		Assertions.assertEquals(new AbsolutePosition(0, 0), outerResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), outerResult.relativeRect().size());
		Assertions.assertInstanceOf(BuildableRenderedUnit.class, outerResult.unit());
		BuildableRenderedUnit buildableUnit = (BuildableRenderedUnit) outerResult.unit();
		Assertions.assertTrue(buildableUnit.wasMarkedFinished());
		Assertions.assertEquals(1, buildableUnit.childLayoutResults().length);
		ChildLayoutResult childUnitEntry = buildableUnit.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childUnitEntry.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), childUnitEntry.relativeRect().size());
		Assertions.assertEquals(unit1, childUnitEntry.unit());
	}

	@Test
	@DisplayName("Can detect implicit unit end")
	public void canDetectImplicitUnitEnd() {
		RenderedUnit unit1 = Mockito.mock(RenderedUnit.class);
		Mockito.when(unit1.fitSize()).thenReturn(new AbsoluteSize(100, 100));
		lineBox.addMarker(new UnitEnterMarker(true, null));
		lineBox.add(unit1);
		List<ChildLayoutResult> results = lineBox.layoutAtPos(new AbsolutePosition(0, 0));
		Assertions.assertEquals(1, results.size());
		ChildLayoutResult outerResult = results.get(0);
		Assertions.assertEquals(new AbsolutePosition(0, 0), outerResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), outerResult.relativeRect().size());
		Assertions.assertInstanceOf(BuildableRenderedUnit.class, outerResult.unit());
		BuildableRenderedUnit buildableUnit = (BuildableRenderedUnit) outerResult.unit();
		Assertions.assertFalse(buildableUnit.wasMarkedFinished());
		Assertions.assertEquals(1, buildableUnit.childLayoutResults().length);
		ChildLayoutResult childUnitEntry = buildableUnit.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childUnitEntry.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), childUnitEntry.relativeRect().size());
		Assertions.assertEquals(unit1, childUnitEntry.unit());
	}

}
