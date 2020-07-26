package everyos.browser.webicity.webribbon;

import everyos.browser.webicity.webribbon.component.WebComponent;
import everyos.browser.webicity.webribbon.shape.Position;
import everyos.engine.ribbon.graphics.GUIRenderer;
import everyos.engine.ribbon.graphics.component.Component;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.Dimension;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.shape.SizePosGroup;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private WebComponent wui;
	
	public WebComponentWrapperUI() {}
	public WebComponentWrapperUI(Component c) {
		super(c);
		autofill = false;
	}
	@Override public ComponentUI create(Component c) {
		return new WebComponentWrapperUI(c);
	};

	@Override protected void calcInternalSize(GUIRenderer r, SizePosGroup sizepos, DrawData data) {
		if (wui!=null) {
			System.out.println("W:"+sizepos.size.width);
			wui.render(r, new everyos.browser.webicity.webribbon.shape.SizePosGroup(
				new Position(),
				new Dimension(sizepos.size.width, sizepos.size.height),
				sizepos.size.width));
		}
	}

	@Override protected void drawInternal(GUIRenderer r, DrawData data) {
		if (wui!=null) {
			everyos.browser.webicity.webribbon.misc.DrawData d = new everyos.browser.webicity.webribbon.misc.DrawData(); 
			wui.paint(r, d);
		}
	}
	
	@Override public void attribute(String name, Object attr) {
		super.attribute(name, attr);
		if (name=="ui") this.wui = (WebComponent) attr;
	}
}
