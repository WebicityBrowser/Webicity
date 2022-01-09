package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import everyos.browser.webicity.webribbon.gui.box.layout.BlockLevelBox;

public class BlockLevelBoxImpTest {

	@Test
	@DisplayName("Box initially has no children")
	public void boxInitiallyHasNoChildren() {
		BlockLevelBoxImp box = new BlockLevelBoxImp(null);
		Assertions.assertEquals(box.getChildren().length, 0);
	}
	
	@Test
	@DisplayName("Add method adds child")
	public void addMethodAddsChild() {
		BlockLevelBoxImp box = new BlockLevelBoxImp(null);
		BlockLevelBoxImp child = new BlockLevelBoxImp(null);
		box.add(child);
		
		Assertions.assertEquals(box.getChildren().length, 1);
		Assertions.assertEquals(box.getChildren()[0], child);
	}
	
	@Test
	@DisplayName("Block-level box with only inline-level box does not wrap child with block-level box")
	public void blockLevelBoxDoesNotWrapChildInlineLevelBox() {
		BlockLevelBoxImp box = new BlockLevelBoxImp(null);
		InlineLevelBoxImp child = new InlineLevelBoxImp(null);
		box.add(child);
		
		box.finish();
		
		Assertions.assertEquals(box.getChildren().length, 1);
		Assertions.assertEquals(box.getChildren()[0], child);
	}
	
	@Test
	@DisplayName("Block-level box with child inline-level box wraps child with block-level box if any other children are block-level")
	public void blockLevelBoxWrapsChildInlineLevelBox() {
		BlockLevelBoxImp box = new BlockLevelBoxImp(null);
		InlineLevelBoxImp child = new InlineLevelBoxImp(null);
		box.add(child);
		box.add(new BlockLevelBoxImp(null));
		
		box.finish();
		
		Assertions.assertEquals(box.getChildren().length, 2);
		Assertions.assertInstanceOf(BlockLevelBox.class, box.getChildren()[0]);
		
		Assertions.assertEquals(box.getChildren()[0].getChildren().length, 1);
		Assertions.assertEquals(box.getChildren()[0].getChildren()[0], child);
	}
	
}
