package everyos.desktop.thready.laf.simple.component.render.unit;

import java.util.Optional;
import java.util.function.Function;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.core.positioning.util.AbsoluteSizeMath;
import everyos.desktop.thready.laf.simple.component.message.DefaultContentMessageHandler;
import everyos.desktop.thready.laf.simple.component.paint.BlockWrappingPainter;

public class BlockWrappingUnit implements Unit {
	
	private final Unit innerUnit;
	private final Box box;
	private final AbsoluteSize computedSize;

	private BlockWrappingUnit(AbsoluteSize computedSize, Box box, Unit innerUnit) {
		this.computedSize = computedSize;
		this.box = box;
		this.innerUnit = innerUnit;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	
	@Override
	public Painter getPainter(Rectangle documentRect) {
		Rectangle innerUnitDocumentRect = getInnerUnitDocumentRect(documentRect);
		return new BlockWrappingPainter(box, documentRect, innerUnit, innerUnitDocumentRect);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		Rectangle innerUnitDocumentRect = getInnerUnitDocumentRect(documentRect);
		MessageHandler innerUnitMessageHandler = innerUnit.getMessageHandler(innerUnitDocumentRect);
		return new DefaultContentMessageHandler(documentRect, box, innerUnitMessageHandler);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return computedSize;
	}
	
	public static BlockWrappingUnit render(AbsoluteSize precomputedSize, Box box, Function<AbsoluteSize, Unit> innerUnitGenerator) {
		AbsoluteSize outerSize = renderOuterContent();
		AbsoluteSize precomputedInnerSize = precomputeInnerSize(precomputedSize, outerSize);
		Unit innerContentUnit = innerUnitGenerator.apply(precomputedInnerSize);
		AbsoluteSize renderedInnerSize = innerContentUnit.getMinimumSize();
		AbsoluteSize finalInnerSize = AbsoluteSizeMath.min(precomputedInnerSize, renderedInnerSize);
		AbsoluteSize renderedSize = AbsoluteSizeMath.sum(outerSize, finalInnerSize);
		AbsoluteSize finalSize = AbsoluteSizeMath.min(precomputedSize, renderedSize);
		
		return new BlockWrappingUnit(finalSize, box, innerContentUnit);
	}

	private static AbsoluteSize renderOuterContent() {
		// TODO Method stub
		return AbsoluteSizeImp.ZERO_SIZE;
	}
	
	private static AbsoluteSize precomputeInnerSize(AbsoluteSize precomputedSize, AbsoluteSize outerSize) {
		// TODO Method stub
		return precomputedSize;
	}

	private Rectangle getInnerUnitDocumentRect(Rectangle documentRect) {
		// TODO Method stub
		return documentRect;
	}
}
