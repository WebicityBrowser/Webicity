package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.PartialUnitPreview;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public class TextUnitGenerator implements UnitGenerator {

	private final Box box;
	private final String text;
	private final Font2D font;
	private final float[] charWidths;
	private final float height;
	
	int builtWidth = 0;
	int windowStart = 0;
	int windowEnd = 0;

	public TextUnitGenerator(Box box, String text, Font2D font, float[] charWidths, ContextSwitch[] switches) {
		this.box = box;
		this.text = text;
		this.font = font;
		this.charWidths = charWidths;
		this.height = font.getMetrics().getHeight();
	}

	@Override
	public Unit getMergedUnits() {
		String substr = text.substring(windowStart, windowEnd);
		float actualHeight = builtWidth > 0 ? height : 0;
		AbsoluteSize size = new AbsoluteSize(builtWidth, actualHeight);
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
	public PartialUnitPreview previewNextUnit() {
		return new PartialUnitPreview() {
			
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
				
				return new AbsoluteSize(width, actualHeight);
			}
			
			private void ensureNotCompleted() {
				if (completed()) {
					throw new IllegalStateException("This generator has already completed!");
				}
			}
			
		};
	}

}
