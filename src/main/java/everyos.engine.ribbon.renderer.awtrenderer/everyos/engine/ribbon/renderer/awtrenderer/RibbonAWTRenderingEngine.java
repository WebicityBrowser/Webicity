package everyos.engine.ribbon.renderer.awtrenderer;

import java.awt.EventQueue;

import everyos.engine.ribbon.core.rendering.RenderingEngine;

public class RibbonAWTRenderingEngine implements RenderingEngine {
	@Override public void queueRenderingTask(Runnable task) {
		EventQueue.invokeLater(task);
	}
}
