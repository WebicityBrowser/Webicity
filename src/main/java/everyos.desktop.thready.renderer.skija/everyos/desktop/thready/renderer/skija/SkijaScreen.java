package everyos.desktop.thready.renderer.skija;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.Screen;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;

public class SkijaScreen implements Screen {

	private final SkijaRenderingPipeline renderingPipeline = new SkijaRenderingPipeline();
	private final long windowId;
	
	private AbsoluteSize oldSize = new AbsoluteSizeImp(0, 0);
	private Component rootComponent;
	private LookAndFeel lookAndFeel;
	
	public SkijaScreen(long glfwWindowId) {
		this.windowId = glfwWindowId;
	}

	@Override
	public void setGUI(Component component, LookAndFeel lookAndFeel) {
		renderingPipeline.invalidate(InvalidationLevel.BOX);
		this.rootComponent = component;
		this.lookAndFeel = lookAndFeel;
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
			rootComponent != null &&
			lookAndFeel != null &&
			windowIsVisible();
	}

	private boolean windowIsVisible() {
		return glfwBool(GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_VISIBLE));
	}
	
	private void updateWindow() {
		AbsoluteSize windowSize = getWindowSize();
		if (windowSize.equals(oldSize)) {
			oldSize = windowSize;
			rescaleViewport(windowSize);
			renderingPipeline.invalidate(InvalidationLevel.BOX);
		}
		renderingPipeline.tick(rootComponent, lookAndFeel, windowSize);
	}

	private AbsoluteSize getWindowSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSizeImp((float) width[0], (float) height[0]);
	}

	private void rescaleViewport(AbsoluteSize windowSize) {
		//TODO: Allow adjusting scaling elsewhere
		//TODO: Detect the actual DPI
		
		int dpi = 96;
		GL11.glViewport(0, 0,
			(int) (windowSize.getWidth() * 96.0 / dpi),
			(int) (windowSize.getHeight() * 96.0 / dpi));
	}
	
	private boolean glfwBool(int bool) {
		return bool == GLFW.GLFW_TRUE;
	}

}
