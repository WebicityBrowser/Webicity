package everyos.desktop.thready.renderer.skija;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.Screen;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.style.StyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.renderer.skija.canvas.SkijaRootCanvas2D;
import io.github.humbleui.skija.DirectContext;

public class SkijaScreen implements Screen {

	private final long windowId;
	private final DirectContext directContext = DirectContext.makeGL();
	
	private AbsoluteSize oldSize = new AbsoluteSizeImp(0, 0);
	private SkijaRenderingPipeline renderingPipeline;
	private SkijaRootCanvas2D currentCanvas;
	
	public SkijaScreen(long glfwWindowId) {
		this.windowId = glfwWindowId;
	}

	@Override
	public void setGUI(Component component, LookAndFeel lookAndFeel, StyleGeneratorRoot styleGeneratorRoot) {
		renderingPipeline = new SkijaRenderingPipeline(windowId, component, lookAndFeel, styleGeneratorRoot);
	}
	
	public boolean isDone() {
		return GLFW.glfwWindowShouldClose(windowId);
	}
	
	public void tick() {
		if (canRenderGui()) {
			GLFW.glfwMakeContextCurrent(windowId);
			updateWindow();
		}
		GLFW.glfwSwapBuffers(windowId);
	}

	public void close() {
		GLFW.glfwDestroyWindow(windowId);
	}
	
	private boolean canRenderGui() {
		return
			renderingPipeline != null &&
			windowIsVisible();
	}

	private boolean windowIsVisible() {
		return
			glfwBool(GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_VISIBLE)) &&
			!glfwBool(GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_ICONIFIED));
	}
	
	private void updateWindow() {
		AbsoluteSize windowSize = getWindowSize();
		if (!windowSize.equals(oldSize)) {
			oldSize = windowSize;
			rescaleViewport(windowSize);
			renderingPipeline.invalidatePipeline(InvalidationLevel.BOX);
			regenerateCanvas(windowSize);
		}
		renderingPipeline.tick(currentCanvas, windowSize);
	}

	private AbsoluteSize getWindowSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSizeImp((float) width[0], (float) height[0]);
	}

	private void rescaleViewport(AbsoluteSize windowSize) {
		// TODO: Allow adjusting scaling elsewhere
		// TODO: Detect the actual system DPI
		int systemDPI = 96;
		int targetDPI = 96;
		GL11.glViewport(0, 0,
			(int) ((float) windowSize.getWidth() * targetDPI / systemDPI),
			(int) ((float) windowSize.getHeight() * targetDPI / systemDPI));
	}
	
	private void regenerateCanvas(AbsoluteSize size) {
		this.currentCanvas = SkijaRootCanvas2D.create(directContext, size);
	}
	
	private boolean glfwBool(int bool) {
		return bool == GLFW.GLFW_TRUE;
	}

}
