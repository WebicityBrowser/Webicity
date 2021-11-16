package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;

public interface ResourceGenerator {

	RibbonFontMetrics getFont(FontInfo info);

}
