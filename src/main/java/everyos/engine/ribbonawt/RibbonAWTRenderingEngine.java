package everyos.engine.ribbonawt;

import java.awt.EventQueue;

import everyos.engine.ribbon.graphics.RenderingEngine;

public class RibbonAWTRenderingEngine implements RenderingEngine {
	@Override public void queueRenderingTask(Runnable task) {
		EventQueue.invokeLater(task);
	}
}
