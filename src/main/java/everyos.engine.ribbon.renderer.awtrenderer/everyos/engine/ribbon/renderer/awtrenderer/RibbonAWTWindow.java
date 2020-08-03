package everyos.engine.ribbon.renderer.awtrenderer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class RibbonAWTWindow {
	private Frame window;
	private Panel panel;
	private Runnable closeEvent;
	private GUIComponentUI ui;
	private UIManager<GUIComponentUI> uimanager;
	@SuppressWarnings("unused")
	private GraphicsDevice screen;

	public RibbonAWTWindow(GraphicsDevice screen) {
		this.screen = screen;
		this.window = new Frame();
		window.setSize(800, 500);
		
		this.panel = new Panel() {
			private static final long serialVersionUID = -8891910348013739659L;
			
			@Override public void paint(Graphics g) {
				GUIRenderer r = new RibbonAWTRenderer(g, new GUIState()); //TODO
				//GUIRenderer r = _r.getBufferedSubcontext(0, 0, panel.getWidth(), panel.getHeight());
				long time = System.currentTimeMillis();
				ui.render(r, new SizePosGroup(
					panel.getWidth(), panel.getHeight(), 
					0, 0, 
					panel.getWidth(), panel.getHeight()), uimanager);
				System.out.println(System.currentTimeMillis()-time);
				time = System.currentTimeMillis();
				ui.paint(r);
				//r.draw();
				System.out.println(System.currentTimeMillis()-time);
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
		/*this.panel.addMouseListener(new java.awt.event.MouseListener() {
			@Override public void mouseClicked(java.awt.event.MouseEvent e) {}
			@Override public void mousePressed(java.awt.event.MouseEvent e) {}
			@Override public void mouseReleased(java.awt.event.MouseEvent e) {
				for (int i = bindings.size()-1; i>=0; i--) {
					if (bindings.get(i).aabb(e.getX(), e.getY())) {
						MouseListener l = (MouseListener) bindings.get(i).component.getAttribute("onrelease");
						if (l==null) return; //TODO: Move to component code
						l.accept(new MouseEvent(e.getX(), e.getY(), e.getButton()));
						break;
					}
				}
			}
			@Override public void mouseEntered(java.awt.event.MouseEvent e) {}
			@Override public void mouseExited(java.awt.event.MouseEvent e) {}
		});
		this.panel.addMouseWheelListener(e->{
			for (int i = bindings.size()-1; i>=0; i--) {
				if (bindings.get(i).aabb(e.getX(), e.getY())) {
					ScrollListener l = (ScrollListener) bindings.get(i).component.getAttribute("onscroll");
					if (l==null) return; //TODO: Move to component code
					l.accept(new ScrollEvent(e.getX(), e.getY()));
					break;
				}
			}
		});*/
		
		Rectangle bounds = screen.getDefaultConfiguration().getBounds();
		this.window.setLocation(bounds.x+100, bounds.y+100);
		this.window.setMinimumSize(new Dimension(400, 400));
	}
	
	public void bind(Component component, UIManager<GUIComponentUI> uimanager) {
		this.ui = uimanager.get(component, new GUIComponentUI() {
			@Override public ComponentUI create(Component c, ComponentUI parent) { return null; }
			@Override public void directive(UIDirective directive) {}
			@Override public void invalidate() {}
			@Override public void render(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {}
			@Override public void paint(GUIRenderer r) {}
			@Override public GUIComponentUI getParent() { return null; }
			@Override public void validate() {}
			@Override public boolean getValidated() { return true; }
			
			@Override public void invalidateLocal() {
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
	
	/*@Override public void invalidate() {
		panel.revalidate();
		panel.repaint();
	}*/
}
