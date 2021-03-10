package everyos.engine.ribbon.renderer.awtrenderer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.event.MouseInputAdapter;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseEvent;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class RibbonAWTWindow {
	private Frame window;
	private Panel panel;
	private Runnable closeEvent;
	private ComponentUI ui;
	private UIManager uimanager;
	@SuppressWarnings("unused")
	private GraphicsDevice screen;
	private ArrayList<ListenerRect> mouseBindings;

	public RibbonAWTWindow(GraphicsDevice screen) {
		this.screen = screen;
		this.window = new Frame();
		this.mouseBindings = new ArrayList<>();
		window.setSize(800, 500);
		
		this.panel = new Panel() {
			private static final long serialVersionUID = -8891910348013739659L;
			private Object oldSize;
			
			@Override public void paint(Graphics g) {
				Renderer _r = new RibbonAWTRenderer(g, new GUIState()); //TODO
				Renderer r = _r.getBufferedSubcontext(0, 0, panel.getWidth(), panel.getHeight());
				if (!window.getSize().equals(oldSize)) ui.invalidateLocal();
				if (!ui.getValidated()) {
					oldSize = window.getSize();
					long time = System.currentTimeMillis();
					ui.render(r, new SizePosGroup(
						panel.getWidth(), panel.getHeight(), 
						0, 0, 
						panel.getWidth(), panel.getHeight()), uimanager);
					ui.validate();
					//System.out.println("RENDER: "+(System.currentTimeMillis()-time));
				}
				ArrayList<ListenerRect> newMouseBindings = new ArrayList<>(mouseBindings.size());
				_r.onPaint((c, x, y, l, h, listener)->{
					newMouseBindings.add(new ListenerRect(new Rectangle(x, y, l, h), c, listener));
				});
				mouseBindings = newMouseBindings;
				long time = System.currentTimeMillis();
				ui.paint(r);
				r.draw();
				//System.out.println("PAINT: "+(System.currentTimeMillis()-time));
			}
			@Override public void update(Graphics g) {
				paint(g);
				g.dispose();
			}
		};
		this.window.add(this.panel);
		this.window.addWindowListener(new WindowListener() {
			@Override public void windowClosing(WindowEvent e) {
				window.dispose(); //
				System.exit(0);
				if (closeEvent!=null) closeEvent.run();
			}
			@Override public void windowOpened(WindowEvent e) {
				EventQueue.invokeLater(()->{
					window.toFront();
					window.setState(Frame.NORMAL);
				});
			}
			@Override public void windowClosed(WindowEvent e) {}
			@Override public void windowIconified(WindowEvent e) {}
			@Override public void windowDeiconified(WindowEvent e) {}
			@Override public void windowActivated(WindowEvent e) {}
			@Override public void windowDeactivated(WindowEvent e) {}
		});
		
		MouseInputAdapter madapter = new MouseInputAdapter() {
			@Override public void mousePressed(java.awt.event.MouseEvent e) {
				emitEvent(e, MouseEvent.PRESS);
			}
			@Override public void mouseReleased(java.awt.event.MouseEvent e) {
				emitEvent(e, MouseEvent.RELEASE);
			}
			@Override public void mouseDragged(java.awt.event.MouseEvent e) {
				emitEvent(e, MouseEvent.DRAG);
			};
			@Override public void mouseMoved(java.awt.event.MouseEvent e) {
				emitEvent(e, MouseEvent.MOVE);
			};
			
			private void emitEvent(java.awt.event.MouseEvent e, int action) {
				boolean isDetermined = false;
				for (int i = mouseBindings.size()-1; i>=0; i--) {
					ListenerRect binding = mouseBindings.get(i);
					Rectangle bounds = binding.getBounds();
					if (e.getX()>=bounds.x&&e.getX()<=bounds.x+bounds.width&&
						e.getY()>=bounds.y&&e.getY()<=bounds.y+bounds.height) {
						
						binding.getListener().accept(
							new MouseEvent(binding.getComponent(), e.getX(), e.getY(), e.getButton(), action, !isDetermined));
						isDetermined = true;
						
						//TODO: Accept should return a boolean value to indicate if we should break
						//This way, our listener can do precision aabb calculations
						
						//Additionally, we will probably want normalized X and Y values in the future
						//We can probably achieve this with an offset determined by the renderer,
						//where 0,0 is the window's top left corner
					} else {
						binding.getListener().accept(
							new MouseEvent(binding.getComponent(), e.getX(), e.getY(), e.getButton(), action, false));
					}
				}
			}
		};
		this.panel.addMouseListener(madapter);
		this.panel.addMouseMotionListener(madapter);
		/*this.panel.addMouseWheelListener(e->{
			for (int i = bindings.size()-1; i>=0; i--) {
				if (bindings.get(i).aabb(e.getX(), e.getY())) {
					ScrollListener l = (ScrollListener) bindings.get(i).component.getAttribute("onscroll");
					if (l==null) return; //TODO: Move to component code
					l.accept(new ScrollEvent(e.getX(), e.getY()));
					break;
				}
			}
		});*/
		
		java.awt.Rectangle bounds = screen.getDefaultConfiguration().getBounds();
		this.window.setLocation(bounds.x+100, bounds.y+100);
		this.window.setMinimumSize(new Dimension(400, 400));
	}
	
	public void bind(Component component, UIManager uimanager) {
		this.ui = uimanager.get(component, new ComponentUI() {
			@Override public void directive(UIDirective directive) {}
			@Override public void invalidate() {}
			@Override public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {}
			@Override public void paint(Renderer r) {}
			@Override public ComponentUI getParent() { return null; }
			@Override public void validate() {}
			@Override public boolean getValidated() { return true; }
			@Override public void hint(int hint) {}
			
			@Override public void invalidateLocal() {
				panel.invalidate();
				panel.repaint();
			}
			@Override public void repaintLocal() {
				panel.invalidate();
				panel.repaint();
			}
		});
		component.bind(ui);
		
		this.uimanager = uimanager;
	}
	
	public void close() {
		window.dispose();
	}

	public void minimize() {
		window.setState(Frame.ICONIFIED);
	}
	public void restore() {
		window.setExtendedState((window.getExtendedState()&Frame.MAXIMIZED_BOTH)==0?Frame.MAXIMIZED_BOTH:Frame.NORMAL);
	}
	
	public void setVisible(boolean visible) {
		window.setVisible(visible);
	}

	public void setTitle(String title) {
		window.setTitle(title);
	}
	public void setIcon(String resource) {
		try {
			window.setIconImage(ImageIO.read(ClassLoader.getSystemClassLoader().getResource(resource)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMinSize(Location location) {
		//TODO: Support the percentages
		window.setMaximumSize(new Dimension(location.x.offset, location.y.offset));
	}

	public void setDecorated(boolean decorated) {
		window.setUndecorated(!decorated);
	}
	
	/*@Override public void invalidate() {
		panel.revalidate();
		panel.repaint();
	}*/
}
