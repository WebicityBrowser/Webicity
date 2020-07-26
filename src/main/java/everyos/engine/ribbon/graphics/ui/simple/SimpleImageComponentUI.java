package everyos.engine.ribbon.graphics.ui.simple;

import everyos.engine.ribbon.graphics.GUIRenderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.shape.SizePosGroup;

public class SimpleImageComponentUI extends SimpleBlockComponentUI {
	//private BufferedImage image;

	public SimpleImageComponentUI() {}
	public SimpleImageComponentUI(Component c) {
		super(c);
	}
	@Override public ComponentUI create(Component c) {
		return new SimpleImageComponentUI(c);
	};
	@Override protected void calcInternalSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		/*if (image!=null) {
			sizepos.x+=image.getWidth();
			sizepos.minIncrease(image.getHeight());
			sizepos.normalize();
		}*/
		super.calcInternalSize(r, sizepos, data);
	}

	@Override protected void drawInternal(GUIRenderer r, DrawData data) {
		/*if (image!=null) {
			Image scaled = image.getScaledInstance((int) bounds.getWidth(), (int) bounds.getHeight(), Image.SCALE_SMOOTH);
			Color fillcolor = (Color) data.attributes.getOrDefault("fill-color", data.attributes.getOrDefault("bg-color", Color.WHITE));
			scaled = rounded(scaled, fillcolor, (float) attributes.getOrDefault("radius", 0f));
			g.drawImage(scaled, 0, 0, null);
		}*/
		super.drawInternal(r, data);
	}
	

	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		/*try {
			if (attr!=null) {
				if (name=="image-url") this.image = ImageIO.read(new URL((String) attr));
				if (name=="image-resource") this.image = ImageIO.read(ClassLoader.getSystemClassLoader().getResource((String) attr));
				if (name=="image-file") this.image = ImageIO.read(new File((String) attr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/*public static BufferedImage rounded(Image image, Color bg, float cornerRadius) {
		//I can use Stack Overflow!
	    int w = image.getWidth(null);
	    int h = image.getHeight(null);
	    BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2 = output.createGraphics();

	    g2.setComposite(AlphaComposite.Src);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(bg);
	    g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius*w, cornerRadius*h));

	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);

	    g2.dispose();

	    return output;
	}*/
}
