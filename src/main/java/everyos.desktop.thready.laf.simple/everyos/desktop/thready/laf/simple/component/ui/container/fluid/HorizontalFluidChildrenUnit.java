package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.component.message.DefaultMessageHandler;

public class HorizontalFluidChildrenUnit implements Unit {

	private final AbsoluteSize size;
	private final FluidChildrenResult[] renderResults;

	public HorizontalFluidChildrenUnit(AbsoluteSize size, FluidChildrenResult[] renderResults) {
		this.size = size;
		this.renderResults = renderResults;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new HorizontalFluidChildrenPainter(documentRect, renderResults);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultMessageHandler();
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.size;
	}

}
