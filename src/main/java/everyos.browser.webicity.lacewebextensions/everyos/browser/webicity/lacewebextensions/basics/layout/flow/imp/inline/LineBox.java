package everyos.browser.webicity.lacewebextensions.basics.layout.flow.imp.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicity.lace.core.shape.RelativeDimension;
import com.github.webicity.lace.core.shape.Size;

public class LineBox<T extends LineBoxItem> {
	
	private static final float INDETERMINATE = RelativeDimension.INDETERMINATE;

	private final List<T> elements = new ArrayList<>();
	private final float maxWidth;
	
	private float currentWidth = 0;
	private float heightOverBaseline = 0;
	private float heightUnderBaseline = 0;
	
	public LineBox(float maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public void addElement(T item) {
		elements.add(item);
		adjustCurrentWidth(item);
		adjustBaselines(item);
	}

	public float getRemainingWidth() {
		if (maxWidth == INDETERMINATE) {
			return INDETERMINATE;
		}
		if (currentWidth > maxWidth) {
			return 0;
		}
		return maxWidth - currentWidth;
	}
	
	public boolean elementFits(T item) {
		if (maxWidth == INDETERMINATE) {
			return true;
		}
		
		float itemWidth = item.getSize().getWidth();
		return itemWidth <= getRemainingWidth();
	}
	
	public float getHeight() {
		return heightOverBaseline + heightUnderBaseline;
	}
	
	public float getBaselineY() {
		return heightOverBaseline;
	}
	
	public int getElementCount() {
		return elements.size();
	}
	
	private void adjustCurrentWidth(T item) {
		currentWidth += item.getSize().getWidth();
	}
	
	private void adjustBaselines(T item) {
		Size itemSize = item.getSize();
		if (itemSize.getHeight() > heightOverBaseline) {
			heightOverBaseline = itemSize.getHeight();
		}
	}
	
}
