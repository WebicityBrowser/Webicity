package everyos.engine.ribbon.renderer.skijarenderer;

import java.awt.EventQueue;

import everyos.engine.ribbon.core.rendering.RenderingEngine;

public class RibbonSkijaRenderingEngine implements RenderingEngine {
	@Override public void queueRenderingTask(Runnable task) {
		EventQueue.invokeLater(task);
	}
}
