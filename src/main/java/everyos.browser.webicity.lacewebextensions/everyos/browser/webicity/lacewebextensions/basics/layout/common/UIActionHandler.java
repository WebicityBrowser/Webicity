package everyos.browser.webicity.lacewebextensions.basics.layout.common;

import com.github.webicity.lace.core.laf.ComponentUI;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.box.BoxStageContext;

public final class UIActionHandler {

	private UIActionHandler() {}
	
	public static void box(ComponentUI ui, BoxStageBox parent, BoxStageContext context) {
		ui.box(parent, context);
	};
	
}
