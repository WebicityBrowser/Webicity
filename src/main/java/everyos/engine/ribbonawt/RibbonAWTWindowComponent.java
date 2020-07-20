package everyos.engine.ribbonawt;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import everyos.engine.ribbon.event.MouseEvent;
import everyos.engine.ribbon.event.MouseListener;
import everyos.engine.ribbon.event.ScrollEvent;
import everyos.engine.ribbon.event.ScrollListener;
import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.component.BufferedComponent;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.UIManager;
import everyos.engine.ribbon.graphics.ui.simple.SimpleUIManager;
import everyos.engine.ribbon.input.MouseBinding;
import everyos.engine.ribbon.shape.Location;

public class RibbonAWTWindowComponent extends Component {
	public Frame window;
	public Panel panel;
	private Runnable closeEvent;
	private GraphicsDevice screen;
	private ArrayList<MouseBinding> bindings = new ArrayList<MouseBinding>();

	public RibbonAWTWindowComponent(GraphicsDevice screen) {
		super(null);
		this.parent = null;
		this.screen = screen;
		this.window = new Frame();
		this.panel = new Panel() {
			private static final long serialVersionUID = -8891910348013739659L;
			
			@Override public void paint(Graphics g) {
				Renderer r = new RibbonAWTRenderer(g); //TODO
				long time = System.currentTimeMillis();
				calcSize(r, null, new DrawData());
				System.out.println(System.currentTimeMillis()-time);
				time = System.currentTimeMillis();
				DrawData data = new DrawData();
				draw(r, data);
				System.out.println(System.currentTimeMillis()-time);
				bindings = data.bindings;
			}
			@Override public void update(Graphics g) {
				paint(g);
				g.dispose();
			}
		};
		this.window.add(this.panel);
		this.window.addWindowListener(new WindowListener() {
			@Override public void windowClosing(WindowEvent e) {
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
		this.panel.addMouseListener(new java.awt.event.MouseListener() {
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
		});
		
		Rectangle bounds = screen.getDefaultConfiguration().getBounds();
		this.window.setLocation(bounds.x+100, bounds.y+100);
		this.window.setMinimumSize(new Dimension(400, 400));
	}
	
	@Override public UIManager getUIManager() {
		return this.uimanager!=null?this.uimanager:(this.uimanager=SimpleUIManager.createUI());
	}
	
	@Override public Component attribute(String name, Object attr) {
		super.attribute(name, attr);
		
		if (name=="decorated") window.setUndecorated(!((boolean) attr)); //TODO: Do I move this to SimpleWindowComponentUI?
		if (name=="size") window.setSize((Dimension) attr); //TODO: Use Location instead
		if (name=="visible") {
			window.setVisible((boolean) attr);
		}
		if (name=="onclose") closeEvent = (Runnable) attr;
		if (name=="title") window.setTitle((String) attr);
		if (name=="icon-resource") {
			try {
				window.setIconImage(ImageIO.read(ClassLoader.getSystemClassLoader().getResource((String) attr)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (name=="icon") window.setIconImage((Image) attr);
		if (name=="position") {
			Rectangle bounds = screen.getDefaultConfiguration().getBounds();
			Point2D position = (Point2D) attr; //TODO: Use Location instead
			window.setLocation(bounds.x+(int) position.getX(), bounds.y+(int) position.getY());
		}
		
		return this;
	}
	
	public BufferedComponent createDefaultComponent() {
		return (BufferedComponent) new BufferedComponent(this)
			.attribute("position", new Location(0, 0, 0, 0))
			.attribute("size", new Location(1, 0, 1, 0));
	}
	
	public void close() {
		window.dispose();
	}
	
	@Override public void invalidate() {
		panel.revalidate();
		panel.repaint();
	}

	public void minimize() {
		window.setState(Frame.ICONIFIED);
	}
	public void restore() {
		window.setExtendedState((window.getExtendedState()&Frame.MAXIMIZED_BOTH)==0?Frame.MAXIMIZED_BOTH:Frame.NORMAL);
	}
}
