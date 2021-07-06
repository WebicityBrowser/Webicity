package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import everyos.browser.webicitybrowser.util.TimeSystem;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.skijarenderer.ImageUtil.Image;

public class RibbonSkijaWindow {
	private long window;
	private boolean running = true;
	private UIManager uiManager;
	private ComponentUI rootComponentUI;
	private ArrayList<ListenerRect> mouseBindings;
	private Dimension oldSize;

	public RibbonSkijaWindow(int id) {
		this.mouseBindings = new ArrayList<>();
		
		Semaphore lock = new Semaphore(0);
		
		new Thread(()->{
			createWindow(id);
			createMouseBindings();
			lock.release();
			runLoop();
			GLFW.glfwDestroyWindow(window);
		}, "GLEventThread").start();
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void bind(Component component, UIManager uimanager) {
		this.rootComponentUI = uimanager.get(component, null);
		component.bind(rootComponentUI);
		System.out.println(rootComponentUI);
		
		this.uiManager = uimanager;
	}
	
	public void close() {
		this.running = false;
	}

	public void minimize() {
		GLFW.glfwIconifyWindow(window);
	}
	
	public void restore() {
		if (GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE) {
			GLFW.glfwRestoreWindow(window);
		} else {
			GLFW.glfwMaximizeWindow(window);
		}
	}
	
	public void setVisible(boolean visible) {
		if (visible) {
			GLFW.glfwShowWindow(window);
		} else {
			GLFW.glfwHideWindow(window);
		}
	}

	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}
	
	public void setIcon(String resource) {
		setIcons(new Image[] {
			ImageUtil.fromInputStream(ClassLoader.getSystemClassLoader().getResourceAsStream(resource))
		});
	}
	
	public void setMinSize(Location location) {
		//TODO: Support the percentages
		GLFW.glfwSetWindowSizeLimits(window, location.x.offset, location.y.offset, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
	}

	public void setDecorated(boolean decorated) {
		//TODO
	}
	
	/////
	
	private void runLoop() {
		RibbonSkijaRenderer root = RibbonSkijaRenderer.of(window);
		TimeSystem.reset();
		while (running&&!GLFW.glfwWindowShouldClose(window)) {
			if (GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_ICONIFIED) == GLFW.GLFW_FALSE) {
				TimeSystem.step();
				root = updateWindow(root);
			}
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
	}
	
	private RibbonSkijaRenderer updateWindow(RibbonSkijaRenderer root) {
		if (rootComponentUI==null) return root;
		
		RibbonSkijaRenderer renderer = root;
		
		// Check if the window size has changed
		Dimension size = getSize();
		if (oldSize==null || oldSize.getWidth()!=size.getWidth() || oldSize.getHeight()!=size.getHeight()) {
			rootComponentUI.invalidateLocal(InvalidationLevel.RENDER);
			renderer = RibbonSkijaRenderer.of(window);
			oldSize = size;
		}
		
		// Recalculate the layout if it is invalid
		if (!rootComponentUI.getValidated(InvalidationLevel.RENDER)) {
			//long time = System.currentTimeMillis();
			//TODO: Fix the memory leak
			rootComponentUI.render(
				root,
				new SizePosGroup(
					size.getWidth(), size.getHeight(), 
					0, 0, 
					size.getWidth(), size.getHeight()),
				uiManager);
			rootComponentUI.validateTo(InvalidationLevel.PAINT);
			//System.out.println("RENDER: "+(System.currentTimeMillis()-time));
		}
		
		// We paint mouse boxes while painting graphics.
		ArrayList<ListenerRect> newMouseBindings = new ArrayList<>(mouseBindings.size());
		root.onPaint((c, x, y, l, h, listener)->{
			newMouseBindings.add(new ListenerRect(new Rectangle(x, y, l, h), c, listener));
		});
		mouseBindings = newMouseBindings;
		
		// Paint and display the UI.
		// The target time is 16ms for paint per frame, max
		//long time = System.currentTimeMillis();
		rootComponentUI.paint(renderer);
		renderer.draw();
		//System.out.println(System.currentTimeMillis()-time);
		
		// Signifies that we don't need to paint unless we are further invalidated
		// Note that, due to a double buffering bug,
		// we ignore this validation level at the time being
		rootComponentUI.validateTo(InvalidationLevel.IGNORE);
		
		return renderer;
	}

	private Dimension getSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		return new Dimension(width[0], height[0]);
	}
	
	private void setIcons(Image[] images) {
		GLFWImage.Buffer imagesBuffer = GLFWImage.create(images.length);
		for (int i=0; i<images.length; i++) {
			final Image image = images[i];
			if (!(image instanceof Image)) {
				throw new RuntimeException("Expected an Image class!");
			}
			final GLFWImage glfwImage = GLFWImage.create();
			glfwImage.set(image.getWidth(), image.getHeight(), image.getBuffer());
			
			imagesBuffer.put(i, glfwImage);
		}
		GLFW.glfwSetWindowIcon(window, imagesBuffer);
	}
	
	private int fixButton(int button) {
		if (button==GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			button = MouseEvent.LEFT_BUTTON;
		} else if (button==GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			button = MouseEvent.RIGHT_BUTTON;
		}
		
		return button;
	}
	
	private int fixAction(int action) {
		if (action==GLFW.GLFW_RELEASE) {
			action = MouseEvent.RELEASE;
		}
		
		return action;
	}
	
	private void emitEvent(int x, int y, int button, int action) {
		boolean isDetermined = false;
		
		for (int i = mouseBindings.size()-1; i>=0; i--) {
			ListenerRect binding = mouseBindings.get(i);
			Rectangle bounds = binding.getBounds();
			if (x>=bounds.getX()&&x<=bounds.getX()+bounds.getWidth()&&
				y>=bounds.getY()&&y<=bounds.getY()+bounds.getHeight()) {
				
				binding.getListener().accept(
					new MouseEvent(binding.getEventTarget(), x, y, button, action, !isDetermined));
				
				isDetermined = true;
				
				//TODO: Accept should return a boolean value to indicate if we should break
				//This way, our listener can do precision aabb calculations
				
				//Additionally, we will probably want normalized X and Y values in the future
				//We can probably achieve this with an offset determined by the renderer,
				//where 0,0 is the window's top left corner
			} else {
				binding.getListener().accept(
					new MouseEvent(binding.getEventTarget(), x, y, button, action, false));
			}
		}
	}

	private void createMouseBindings() {
		GLFW.glfwSetMouseButtonCallback(window, ($, button, action, mods)->{
			button = fixButton(button);
			action = fixAction(action);
			
			double[] x = new double[1], y = new double[1];
			GLFW.glfwGetCursorPos(window, x, y);
			emitEvent((int) x[0], (int) y[0], button, action);
		});
		
		GLFW.glfwSetCursorPosCallback(window, ($, x, y)->{
			if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT)==GLFW.GLFW_PRESS) {
				emitEvent((int) x, (int) y, GLFW.GLFW_MOUSE_BUTTON_LEFT, MouseEvent.DRAG);
			} else if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT)==GLFW.GLFW_PRESS) {
				emitEvent((int) x, (int) y, GLFW.GLFW_MOUSE_BUTTON_RIGHT, MouseEvent.DRAG);
			} else {
				emitEvent((int) x, (int) y, 0, MouseEvent.MOVE);
			}
		});
	}

	private void createWindow(int id) {
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		this.window = GLFW.glfwCreateWindow(800, 600, "Untitled Application", id, MemoryUtil.NULL);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GLFW.glfwSwapInterval(1);
		GLFW.glfwSetWindowPos(window, 100, 100);
	}
	
	static {
		if (!GLFW.glfwInit()) {
			throw new RuntimeException("GLFW init failed!");
		}
	}
}
