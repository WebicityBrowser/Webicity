package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.ArrayList;

import org.jetbrains.skija.DirectContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.CharEvent;
import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.graphics.font.FontWeight;
import everyos.engine.ribbon.core.input.keyboard.Key;
import everyos.engine.ribbon.core.input.keyboard.KeyboardEvent;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.ResourceGenerator;
import everyos.engine.ribbon.core.rendering.SharedRendererContext;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.skijarenderer.event.ListenerPaintListener;
import everyos.engine.ribbon.renderer.skijarenderer.event.ListenerRect;
import everyos.engine.ribbon.renderer.skijarenderer.event.MouseEventBuilder;
import everyos.engine.ribbon.renderer.skijarenderer.util.ImageUtil;
import everyos.engine.ribbon.renderer.skijarenderer.util.ImageUtil.Image;
import everyos.engine.ribbon.renderer.skijarenderer.util.KeyLookupUtil;

public class RibbonSkijaWindow {
	
	// TODO: Seperate graphics and input components
	
	private final SharedRendererContext rendererContext = new RibbonSkijaSharedRendererContext();
	private final DirectContext context;	
	private final long window;
	
	private boolean running = true;
	private UIManager uiManager;
	private ComponentUI rootComponentUI;
	private ArrayList<ListenerRect> mouseBindings = new ArrayList<>();
	private ArrayList<EventListener<UIEvent>> generalEventBindings = new ArrayList<>();
	private Dimension oldSize;
	private boolean nextFrameRequiresRedraw = false;
	private RibbonSkijaRenderer root;

	public RibbonSkijaWindow(int id, boolean decorated) {
		if (!SkijaRenderingThread.isCurrent()) {
			throw new RuntimeException("Running on wrong thread!");
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, decorated ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);		
		this.window = createWindow(id);
		
		this.context = DirectContext.makeGL();
		this.root = RibbonSkijaRenderer.of(window, context);
		
		createMouseBindings();
		createKeyboardBindings();
	}

	
	// PUBLIC METHODS START
	
	public void bind(Component component, UIManager uimanager) {
		this.rootComponentUI = uimanager.get(component, null);
		component.bind(rootComponentUI);
		
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
		rootComponentUI.invalidate(InvalidationLevel.PAINT);
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
		GLFW.glfwSetWindowSizeLimits(window, location.getX().getAbsolute(), location.getY().getAbsolute(), GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
	}
	
	public Dimension getSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		return new Dimension(width[0], height[0]);
	}
	
	public void setPosition(int x, int y) {
		GLFW.glfwSetWindowPos(window, x, y);
	}
	
	public Position getPosition() {
		int[] x = new int[1];
		int[] y = new int[1];
		GLFW.glfwGetWindowPos(window, x, y);
		
		return new Position(x[0], y[0]);
	}
	
	// PUBLIC METHODS END
	
	private long createWindow(int id) {
		long window = GLFW.glfwCreateWindow(800, 600, "Untitled Application", id, MemoryUtil.NULL);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		GLFW.glfwSwapInterval(1);
		GLFW.glfwSetWindowPos(window, 100, 100);
		
		return window;
	}
	
	private void runLoop() {
		if (GLFW.glfwWindowShouldClose(window) || !running) {
			GLFW.glfwDestroyWindow(window);
			return;
		}
		
		if (GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_ICONIFIED) == GLFW.GLFW_FALSE) {
			GLFW.glfwMakeContextCurrent(window);
			root = updateWindow(root);
		}
		GLFW.glfwSwapBuffers(window);
		
		SkijaRenderingThread.run(()->runLoop());
	}
	
	private RibbonSkijaRenderer updateWindow(RibbonSkijaRenderer root) {
		if (rootComponentUI == null) {
			return root;
		}
		
		rendererContext.getTimeSystem().step();
		
		RibbonSkijaRenderer renderer = root;
		
		// Check if the window size has changed
		Dimension size = getSize();
		if (oldSize == null || oldSize.getWidth() != size.getWidth() || oldSize.getHeight() != size.getHeight()) {
			rootComponentUI.invalidateLocal(InvalidationLevel.RENDER);
			renderer = RibbonSkijaRenderer.of(window, context);
			oldSize = size;
		}
		
		render(renderer, size);
		paint(renderer, size);
		
		return renderer;
	}

	private void render(Renderer renderer, Dimension size) {
		// Recalculate the layout if it is invalid
		if (!rootComponentUI.getValidated(InvalidationLevel.RENDER)) {
			//long time = System.currentTimeMillis();
			//TODO: Fix the memory leak
			rootComponentUI.render(
				createRendererData(renderer.getResourceGenerator(), size),
				new SizePosGroup(
					size.getWidth(), size.getHeight(), 
					0, 0, 
					size.getWidth(), size.getHeight()),
				new DefaultRenderContext());
			rootComponentUI.validateTo(InvalidationLevel.PAINT);
			//System.out.println("RENDER: "+(System.currentTimeMillis()-time));
		}
	}

	private void paint(Renderer renderer, Dimension size) {
		if (!rootComponentUI.getValidated(InvalidationLevel.PAINT) || nextFrameRequiresRedraw) {
			nextFrameRequiresRedraw = !rootComponentUI.getValidated(InvalidationLevel.PAINT);
			
			// We paint event listeners while painting graphics.
			mouseBindings.trimToSize();
			mouseBindings.clear();
			generalEventBindings.trimToSize();
			generalEventBindings.clear();
			
			root.onPaint(new ListenerPaintListener() {
				@Override
				public void onPaint(UIEventTarget c, int[] bounds, EventListener<MouseEvent> listener) {
					mouseBindings.add(new ListenerRect(new Rectangle(bounds), c, listener));
				}
	
				@Override
				public void onPaint(EventListener<UIEvent> listener) {
					generalEventBindings.add(listener);
				}
			});
			
			// Paint and display the UI.
			// The target time is 16ms for paint per frame, max
			//long time = System.currentTimeMillis();
			rootComponentUI.paint(createRendererData(renderer.getResourceGenerator(), size), new DefaultPaintContext());
			renderer.draw();
			//System.out.println(System.currentTimeMillis()-time);
		}
	}

	private RendererData createRendererData(ResourceGenerator gen, Dimension size) {
		RendererData data = new RendererDataImp(
			new GUIState(),
			new int[] {0, 0, size.getWidth(), size.getHeight()},
			new int[] {0, 0, size.getWidth(), size.getHeight()},
			rendererContext);
		data.getState().setFont(gen.getFont(FontInfo.getByName("Open Sans", 12, FontWeight.NORMAL)));
		
		return data;
	}
	
	private void setIcons(Image[] images) {
		GLFWImage.Buffer imagesBuffer = GLFWImage.create(images.length);
		for (int i = 0; i < images.length; i++) {
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
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			button = MouseEvent.LEFT_BUTTON;
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			button = MouseEvent.RIGHT_BUTTON;
		}
		
		return button;
	}
	
	private int fixAction(int action) {
		if (action == GLFW.GLFW_RELEASE) {
			action = MouseEvent.RELEASE;
		}
		
		return action;
	}
	
	private void emitMouseEvent(int x, int y, int button, int action) {
		boolean isDetermined = false;
		Position pos = getPosition();
		
		MouseEventBuilder mouseEventBuilder = new MouseEventBuilder();
		mouseEventBuilder.setAbsoluteCords(x, y);
		mouseEventBuilder.setScreenCords(x+pos.getX(), y+pos.getY());
		mouseEventBuilder.setButton(button);
		mouseEventBuilder.setAction(action);
		
		for (int i = mouseBindings.size()-1; i >= 0; i--) {
			ListenerRect binding = mouseBindings.get(i);
			Rectangle bounds = binding.getBounds();
			
			mouseEventBuilder.setEventTarget(binding.getEventTarget());
			mouseEventBuilder.setRelativeCords(x-bounds.getX(), y-bounds.getY());
			
			if (x >= bounds.getX() && x <= bounds.getX()+bounds.getWidth() &&
				y >= bounds.getY() && y <= bounds.getY()+bounds.getHeight()) {
				
				mouseEventBuilder.setInternal(!isDetermined);
				isDetermined = true;
			} else {
				mouseEventBuilder.setInternal(false);
			}
			
			//TODO: Accept should return a boolean value to indicate if we should break
			//This way, our listener can do precision aabb calculations
			
			//Additionally, we will probably want normalized X and Y values in the future
			//We can probably achieve this with an offset determined by the renderer,
			//where (0, 0) is the window's top left corner
			binding.getListener().accept(mouseEventBuilder.build());
		}
	}
	
	private void emitCharEvent(CharEvent e) {
		for (EventListener<UIEvent> binding: generalEventBindings) {
			binding.accept(e);
		}
	}
	
	private void emitKeyboardEvent(KeyboardEvent e) {
		for (EventListener<UIEvent> binding: generalEventBindings) {
			binding.accept(e);
		}
	}

	private void createMouseBindings() {
		this.mouseBindings = new ArrayList<>();
		
		GLFW.glfwSetMouseButtonCallback(window, ($, button, action, mods)->{
			button = fixButton(button);
			action = fixAction(action);
			
			double[] x = new double[1], y = new double[1];
			GLFW.glfwGetCursorPos(window, x, y);
			emitMouseEvent((int) x[0], (int) y[0], button, action);
		});
		
		GLFW.glfwSetCursorPosCallback(window, ($, x, y)->{
			if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT)==GLFW.GLFW_PRESS) {
				emitMouseEvent((int) x, (int) y, MouseEvent.LEFT_BUTTON, MouseEvent.DRAG);
			} else if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT)==GLFW.GLFW_PRESS) {
				emitMouseEvent((int) x, (int) y, MouseEvent.RIGHT_BUTTON, MouseEvent.DRAG);
			} else {
				emitMouseEvent((int) x, (int) y, 0, MouseEvent.MOVE);
			}
		});
	}
	
	private void createKeyboardBindings() {
		GLFW.glfwSetCharCallback(window, ($, cp)->{
			String ch = new String(new int[] {cp}, 0, 1);
			CharEvent e = ()->ch;
			emitCharEvent(e);
		});
		
		GLFW.glfwSetKeyCallback(window, ($, kc, $1, action, $2) -> {
			Key key = KeyLookupUtil.query(kc);
			KeyboardEvent e = new KeyboardEvent() {
				@Override
				public Key getKey() {
					return key;
				}

				@Override
				public int getAction() {
					if (action == GLFW.GLFW_RELEASE) {
						return KeyboardEvent.KEY_RELEASE;
					} else if (action == GLFW.GLFW_PRESS) {
						return KeyboardEvent.KEY_PRESS;
					} else if (action == GLFW.GLFW_REPEAT) {
						return KeyboardEvent.KEY_HOLD;
					}
					return -1;
				}
			};
			
			emitKeyboardEvent(e);
		});
	}
	
	private class DefaultRenderContext implements RenderContext {
		@Override
		public UIManager getUIManager() {
			return uiManager;
		}

		@Override
		public ResourceGenerator getResourceGenerator() {
			return root.getResourceGenerator();
		}
	}
	
	private class DefaultPaintContext implements PaintContext {
		@Override
		public Renderer getRenderer() {
			return root;
		}
	}
	
	static {
		if (!GLFW.glfwInit()) {
			throw new RuntimeException("GLFW init failed!");
		}
	}

	public void start() {
		SkijaRenderingThread.run(()->runLoop());
	}
	
}
