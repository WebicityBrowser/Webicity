package everyos.browser.webicity.lacewebextensions.basics.layout.flow;

import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.layout.LayoutManager;
import com.github.webicity.lace.core.layout.LayoutManagerUI;

public class FlowLayoutManager implements LayoutManager {

	@Override
	public LayoutManagerUI createUI(ComponentUI parentUI) {
		return new FlowLayoutManagerUI(parentUI);
	}

}
