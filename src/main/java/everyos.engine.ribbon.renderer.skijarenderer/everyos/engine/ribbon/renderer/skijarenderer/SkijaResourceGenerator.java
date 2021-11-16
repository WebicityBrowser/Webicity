package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.Map;
import java.util.WeakHashMap;

import everyos.engine.ribbon.core.graphics.font.FontInfo;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.rendering.ResourceGenerator;

public class SkijaResourceGenerator implements ResourceGenerator {
	
	private final static Map<FontInfo, RibbonFontMetrics> fonts = new WeakHashMap<>();
	
	@Override
	public RibbonFontMetrics getFont(FontInfo info) {
		return fonts.computeIfAbsent(info, _1 -> RibbonSkijaFont.of(info));
	}
	
}
