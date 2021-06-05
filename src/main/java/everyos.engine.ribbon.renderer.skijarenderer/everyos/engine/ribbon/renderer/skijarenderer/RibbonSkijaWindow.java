package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.skijarenderer.ImageUtil.Image;

public class RibbonSkijaWindow {
	@SuppressWarnings("unused")
	private Runnable closeEvent;
	private ComponentUI ui;
	private UIManager uimanager;
	private ArrayList<ListenerRect> mouseBindings;
	private long window;
	private boolean running = true;
	private Dimension oldSize;
	private boolean maximized;

	public RibbonSkijaWindow(int id) {
		
		//GL.create();
		
		this.mouseBindings = new ArrayList<>();
		
		Semaphore lock = new Semaphore(0);
		
		new Thread(()->{
			GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
			window = GLFW.glfwCreateWindow(800, 600, "Untitled Application", id, MemoryUtil.NULL);
			GLFW.glfwMakeContextCurrent(window);
			GL.createCapabilities();
			GLFW.glfwSwapInterval(3);
			
			GLFW.glfwSetWindowPos(window, 100, 100);
			
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
			
			lock.release();
			
			RibbonSkijaRenderer root = RibbonSkijaRenderer.of(window);
			while (running&&!GLFW.glfwWindowShouldClose(window)) {
				root = updateWindow(root);
				GLFW.glfwSwapBuffers(window);
				GLFW.glfwPollEvents();
			}
			GLFW.glfwDestroyWindow(window);
		}, "GLEventThread").start();
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private RibbonSkijaRenderer updateWindow(RibbonSkijaRenderer root) {
		if (ui==null) return root;
		
		RibbonSkijaRenderer renderer = root;
		
		Dimension size = getSize();
		if (oldSize==null || oldSize.getWidth()!=size.getWidth() || oldSize.getHeight()!=size.getHeight()) {
			ui.invalidateLocal(InvalidationLevel.RENDER);
			renderer = RibbonSkijaRenderer.of(window);
		}
		
		oldSize = size;
		
		if (!ui.getValidated(InvalidationLevel.RENDER)) {
			//long time = System.currentTimeMillis();
			//TODO: Fix the memory leak
			ui.render(root, new SizePosGroup(
				size.getWidth(), size.getHeight(), 
				0, 0, 
				size.getWidth(), size.getHeight()), uimanager);
			ui.validateTo(InvalidationLevel.PAINT);
			//System.out.println("RENDER: "+(System.currentTimeMillis()-time));
		}
		ArrayList<ListenerRect> newMouseBindings = new ArrayList<>(mouseBindings.size());
		root.onPaint((c, x, y, l, h, listener)->{
			newMouseBindings.add(new ListenerRect(new Rectangle(x, y, l, h), c, listener));
		});
		mouseBindings = newMouseBindings;
		//long time = System.currentTimeMillis();
		ui.paint(renderer);
		
		renderer.draw();
		//System.out.println(System.currentTimeMillis()-time);
		
		ui.validateTo(InvalidationLevel.IGNORE);
		
		return renderer;
	}

	private Dimension getSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		return new Dimension(width[0], height[0]);
	}

	public void bind(Component component, UIManager uimanager) {
		this.ui = uimanager.get(component, new ComponentUI() {
			@Override public void directive(UIDirective directive) {}
			@Override public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {}
			@Override public void paint(Renderer r) {}
			@Override public ComponentUI getParent() { return null; }
			@Override public void invalidate(InvalidationLevel level) {}
			@Override public void validateTo(InvalidationLevel level) {}
			@Override public boolean getValidated(InvalidationLevel level) { return true; }		
			@Override public void invalidateLocal(InvalidationLevel level) {}
		});
		component.bind(ui);
		
		this.uimanager = uimanager;
	}
	
	public void close() {
		this.running = false;
	}

	public void minimize() {
		GLFW.glfwIconifyWindow(window);
	}
	
	public void restore() {
		if (maximized) {
			GLFW.glfwRestoreWindow(window);
		} else {
			GLFW.glfwMaximizeWindow(window);
		}
		maximized = !maximized;
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

	public void setMinSize(Location location) {
		//TODO: Support the percentages
		GLFW.glfwSetWindowSizeLimits(window, location.x.offset, location.y.offset, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
	}

	public void setDecorated(boolean decorated) {
		//TODO
	}
	
	/*@Override public void invalidate() {
		panel.revalidate();
		panel.repaint();
	}*/
	
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
	
	static {
		if (!GLFW.glfwInit()) {
			throw new RuntimeException("GLFW init failed!");
		}
	}
}
