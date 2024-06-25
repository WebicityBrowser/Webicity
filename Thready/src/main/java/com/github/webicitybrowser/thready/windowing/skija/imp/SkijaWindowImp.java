package com.github.webicitybrowser.thready.windowing.skija.imp;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.EncodedBytesImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaScreen;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;

public class SkijaWindowImp implements SkijaWindow {

	private final long windowId;
	private final SkijaScreen screen;
	
	private boolean windowClosed = false;

	public SkijaWindowImp(long windowId, SkijaGraphicsSystem graphicsSystem) {
		this.windowId = windowId;
		this.screen = new SkijaScreenImp(this, windowId, graphicsSystem);
	}
	
	@Override
	public SkijaScreen getScreen() {
		return this.screen;
	}
	
	@Override
	public void close() {
		if (this.windowClosed) {
			return;
		}
		this.windowClosed = true;
		GLFW.glfwDestroyWindow(windowId);
	}
	
	@Override
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowId, title);
	}

	@Override
	public void setIcons(ImageSource[] icons) {
		GLFWImage.Buffer iconsBuffer = GLFWImage.create(icons.length);
		for (int i = 0; i < icons.length; i++) {
			if (icons[i] instanceof EncodedBytesImageSource bytesImageSource) {
				GLFWImage image = loadImage(bytesImageSource);
				iconsBuffer.put(i, image);
			} else {
				throw new UnsupportedOperationException("Unsupported image source type: " + icons[i].getClass().getName());
			}
		}
		GLFW.glfwSetWindowIcon(windowId, iconsBuffer);
	}

	//

	@Override
	public void setDecorated(boolean decorated) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_DECORATED, glfwBool(decorated));
	}
	
	@Override
	public void setVisible(boolean visible) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_VISIBLE, glfwBool(visible));
	}
	
	@Override
	public AbsoluteSize getSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSize((float) width[0], (float) height[0]);
	}
	
	//
	
	@Override
	public void minimize() {
		GLFW.glfwIconifyWindow(windowId);
	}
	
	@Override
	public void restore() {
		if (isMaximized()) {
			GLFW.glfwRestoreWindow(windowId);
		} else {
			GLFW.glfwMaximizeWindow(windowId);
		}
	}

	//
	
	@Override
	public void setPosition(AbsolutePosition position) {
		GLFW.glfwSetWindowPos(windowId, (int) position.x(), (int) position.y());
	}
	
	@Override
	public AbsolutePosition getPosition() {
		int[] xpos = new int[1], ypos = new int[1];
		GLFW.glfwGetWindowPos(windowId, xpos, ypos);
		return new AbsolutePosition(xpos[0], ypos[0]);
	}

	//
	
	@Override
	public boolean closed() {
		return windowClosed;
	}

	@Override
	public void tick() {
		closeWindowIfShouldClose();
		if (!windowClosed) {
			screen.tick();
		}
	}
	
	//
	
	private void closeWindowIfShouldClose() {
		if (GLFW.glfwWindowShouldClose(windowId)) {
			close();
		}
	}

	private int glfwBool(boolean bool) {
		return bool ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
	}
	
	private boolean isMaximized() {
		return GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
	}

	private GLFWImage loadImage(EncodedBytesImageSource bytesImageSource) {
		byte[] bytes = bytesImageSource.bytes();
		Image skijaImage = Image.makeDeferredFromEncodedBytes(bytes);
		Bitmap buffer = Bitmap.makeFromImage(skijaImage);
		// Make an ImageInfo that uses RGBA
		ImageInfo imageInfo = new ImageInfo(buffer.getWidth(), buffer.getHeight(), ColorType.RGBA_8888, ColorAlphaType.PREMUL);
		byte[] bufferBytes = buffer.readPixels(imageInfo, buffer.getRowBytes(), 0, 0);
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferBytes.length);
		byteBuffer.put(bufferBytes);
		byteBuffer.flip();
		return GLFWImage.create()
			.set(skijaImage.getWidth(), skijaImage.getHeight(), byteBuffer);
	}
	
	//

	public static SkijaWindow create(SkijaGraphicsSystem graphicsSystem) {
		initGlfw();
		// Trying to set the window attrib after creation doesn't seem to work
		// TODO: Don't assume the window is undecorated
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		long windowId = GLFW.glfwCreateWindow(800, 600, "Untitled Application", 0, 0);
		GLFW.glfwMakeContextCurrent(windowId);
		GL.createCapabilities();
		GLFW.glfwSetWindowPos(windowId, 200, 100);
		GLFW.glfwSwapInterval(0);
		
		SkijaWindow window = new SkijaWindowImp(windowId, graphicsSystem);
		
		return window;
	}
	
	private static void initGlfw() {
		if (GLFW.glfwInit() == false) {
			throw new Error("GLFW init failed");
		}
	}

}
