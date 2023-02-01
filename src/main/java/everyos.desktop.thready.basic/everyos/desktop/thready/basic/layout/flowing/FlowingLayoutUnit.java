package everyos.desktop.thready.basic.layout.flowing;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;

public class FlowingLayoutUnit implements Unit {

	private final FlowingLayoutResult[] resultsToPaint;
	
	public FlowingLayoutUnit(FlowingLayoutResult[] resultsToPaint) {
		this.resultsToPaint = resultsToPaint;
	}
	
	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowingLayoutPainter(documentRect, resultsToPaint);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSizeImp.ZERO_SIZE;
	}

}
