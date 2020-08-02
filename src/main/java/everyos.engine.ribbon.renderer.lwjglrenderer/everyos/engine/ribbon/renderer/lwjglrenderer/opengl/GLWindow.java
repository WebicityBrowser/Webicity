package everyos.engine.ribbon.renderer.lwjglrenderer.opengl;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;

public class GLWindow {
	public long id;
	private Runnable drawFunc;

	public GLWindow(long monitor, String title) {
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		
		this.id = GLFW.glfwCreateWindow(800, 500, title, monitor, MemoryUtil.NULL);
		
		GLFW.glfwMakeContextCurrent(id);
		GL.createCapabilities();
		GLFW.glfwSwapInterval(1);
		
		GLFW.glfwSetKeyCallback(id, new GLFWKeyCallbackI() {
			@Override public void invoke(long window, int key, int scancode, int action, int mods) {
				
			}
		});
	}
	
	public GLWindow(long monitor) {
		this(monitor, "Untitled Application");
	}
	
	public void setVisible(boolean visible) {
		if (visible) {
			GLFW.glfwShowWindow(id);
		} else GLFW.glfwHideWindow(id);
	}
	
	public void setUndecorated(boolean undecorated) {
		//TODO:
		
	}
	
	public boolean closing() {
		return GLFW.glfwWindowShouldClose(id);
	}
	
	public void dispose() {
		Callbacks.glfwFreeCallbacks(id);
		GLFW.glfwDestroyWindow(id);
	}

	public void process() {
		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwPollEvents();
		GL33.glClearColor(1.0f, 0.0f, 1.0f, 0.0f);
		GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
		if (drawFunc!=null) this.drawFunc.run();
		GLFW.glfwSwapBuffers(id);
	}
	
	/*public void setIcons(BufferedImage... icon) throws IOException {
	    Buffer buffer = GLFWImage.create(icon.length);
	    for (int i=0; i<icon.length; i++)
	    	buffer.put(i, GLFWImage.create().set(icon[i].getWidth(null), icon[i].getHeight(null), GLUtil.convertImage(icon[i])));
		GLFW.glfwSetWindowIcon(id, buffer);
	}*/

	public void setDraw(Runnable drawFunc) {
		this.drawFunc = drawFunc;
	}

	public void setTitle(String name) {
		GLFW.glfwSetWindowTitle(id, name);
	}

	public void setSize(Dimension d) {
		GLFW.glfwSetWindowSize(id, d.width, d.height);
	}
}
