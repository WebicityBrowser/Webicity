package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BlockWrappingPainter;

public class BlockWrappingUnit implements Unit {

	private final Box box;
	private final AbsoluteSize finalSize;
	private final Unit innerUnit;

	public BlockWrappingUnit(Box box, AbsoluteSize finalSize, Unit innerUnit) {
		this.box = box;
		this.finalSize = finalSize;
		this.innerUnit = innerUnit;
	}
	
	@Override
	public AbsoluteSize getMinimumSize() {
		return this.finalSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		Painter innerPainter = innerUnit.getPainter(documentRect);
		return new BlockWrappingPainter(box, documentRect, innerPainter);
	}
	
	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new NoopMessageHandler();
	}
	
	public static Unit render(Box box, AbsoluteSize precomputedSize, Function<AbsoluteSize, Unit> innerUnitGenerator) {
		Unit innerContentUnit = innerUnitGenerator.apply(precomputedSize);
		AbsoluteSize finalSize = innerContentUnit.getMinimumSize();
		return new BlockWrappingUnit(box, finalSize, innerContentUnit);
	}
	
}
