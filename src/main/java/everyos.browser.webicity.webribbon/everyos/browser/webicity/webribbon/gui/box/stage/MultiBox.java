package everyos.browser.webicity.webribbon.gui.box.stage;

import java.util.ArrayList;
import java.util.List;

public interface MultiBox extends BoxingStageBox, PaintStageBox, RenderStageBox, StyleStageBox {

	MultiBox[] getChildren();
	void setChildren(List<MultiBox> children);
	
	default void setChildren(MultiBox[] children) {
		setChildren(new ArrayList<>(List.of(children)));
	};
	
	// We don't use Cloneable as we want to avoid having to handle CloneNotSupportedException
	MultiBox duplicate();
	
	//TODO: Add conversion methods between stages
	
}
