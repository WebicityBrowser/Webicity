package everyos.desktop.thready.laf.simple.component.ui.text;

import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.unit.ContextSwitch;
import everyos.desktop.thready.core.gui.stage.render.unit.NextUnitInfo;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.gui.stage.render.unit.UnitGenerator;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;

public class TextUnitGenerator implements UnitGenerator {
	
	private final Box box;
	private final String text;
	private final LoadedFont font;
	private final float[] charWidths;
	private final float height;
	
	int builtWidth = 0;
	int windowStart = 0;
	int windowEnd = 0;

	public TextUnitGenerator(Box box, String text, LoadedFont font, float[] charWidths) {
		this.box = box;
		this.text = text;
		this.font = font;
		this.charWidths = charWidths;
		this.height = font.getMetrics().getHeight();
	}

	@Override
	public Unit getMergedUnits() {
		String substr = text.substring(windowStart, windowEnd);
		AbsoluteSize size = new AbsoluteSizeImp(builtWidth, height);
		Unit textUnit = new TextUnit(box, substr, font, size);
		
		builtWidth = 0;
		windowStart = windowEnd;
		
		return textUnit;
	}
	
	@Override
	public boolean completed() {
		return windowEnd == text.length();
	}
	
	@Override
	public NextUnitInfo getNextUnitInfo(ContextSwitch[] contextSwitches) {
		return new NextUnitInfo() {
			
			@Override
			public void append() {
				ensureNotCompleted();
				builtWidth += charWidths[windowEnd];
				windowEnd++;
			}
			
			@Override
			public AbsoluteSize sizeAfterAppend() {
				ensureNotCompleted();
				float actualHeight = builtWidth > 0 ? height : 0;
				float width = builtWidth + charWidths[windowEnd];
				
				return new AbsoluteSizeImp(width, actualHeight);
			}
			
			private void ensureNotCompleted() {
				if (completed()) {
					throw new IllegalStateException("This generator has already completed!");
				}
			}
			
		};
	}

}
