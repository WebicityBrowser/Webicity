package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.block.context.inline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.FixedRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.LineBoxContainer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;

public class LineBoxContainerTest {
	
	private LineBoxContainer container;

	@BeforeEach
	public void setup() {
		container = new LineBoxContainer(
			Mockito.mock(GlobalRenderContext.class),
			new AbsoluteSize(50, 50),
			new HorizontalLineDimensionConverter(),
			new ContextSwitch[0]
		);
	}

	@Test
	@DisplayName("Can add fluid box to line box container")
	public void canAddFluidBoxToContainer() {
		Box box = createFluidBox(new AbsoluteSize(40, 50));
		container.addBox(box);
		LayoutResult layoutResult = container.layout();
		Assertions.assertEquals(new AbsoluteSize(40, 50), layoutResult.fitSize());
		Assertions.assertEquals(1, layoutResult.childLayoutResults().length);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Box createFluidBox(AbsoluteSize... unitSizes) {
		Box box = Mockito.mock(Box.class);
		Mockito.when(box.isFluid()).thenReturn(true);

		RenderedUnit[] units = new RenderedUnit[unitSizes.length];
		for (int i = 0; i < unitSizes.length; i++) {
			units[i] = Mockito.mock(RenderedUnit.class);
			Mockito.when(units[i].preferredSize()).thenReturn(unitSizes[i]);
		}

		FixedRenderedUnitGenerator<RenderedUnit> unitGenerator = new FixedRenderedUnitGenerator<>(units);

		UIDisplay<?, Box, RenderedUnit> display = Mockito.mock(UIDisplay.class);
		Mockito
			.when(display.renderBox(box, Mockito.any(), Mockito.any()))
			.thenReturn(unitGenerator);
		Mockito
			.when(box.display())
			.thenReturn((UIDisplay) display);

		return box;
	}

}
