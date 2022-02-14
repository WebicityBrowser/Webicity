package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

public class InlineLevelBoxImpTest {

	/*@Test
	@DisplayName("Box initially has no children")
	public void boxInitiallyHasNoChildren() {
		InlineLevelBoxImp box = new InlineLevelBoxImp(null);
		Assertions.assertEquals(box.getChildren().length, 0);
	}
	
	@Test
	@DisplayName("Add method adds child")
	public void addMethodAddsChild() {
		InlineLevelBoxImp box = new InlineLevelBoxImp(null);
		InlineLevelBoxImp child = new InlineLevelBoxImp(null);
		box.add(child);
		
		Assertions.assertEquals(box.getChildren().length, 1);
		Assertions.assertEquals(box.getChildren()[0], child);
	}
	
	@Test
	@DisplayName("Box with block-level children intergrates into parent properly")
	public void boxWithBlockLevelChildIntegratesToParent() {
		BlockLevelBoxImp parent = new BlockLevelBoxImp(null);
		InlineLevelBoxImp box = new InlineLevelBoxImp(parent);
		BlockLevelBoxImp child1 = new BlockLevelBoxImp(null);
		InlineLevelBoxImp child2 = new InlineLevelBoxImp(null);
		InlineLevelBoxImp child3 = new InlineLevelBoxImp(null);
		
		box.add(child1);
		box.add(child2);
		box.add(child3);
		box.finish();
		
		Assertions.assertEquals(parent.getChildren().length, 2);
		Assertions.assertEquals(parent.getChildren()[0], child1);
		Assertions.assertInstanceOf(BlockLevelBox.class, parent.getChildren()[1]);
		
		MultiBox generatedBox = parent.getChildren()[1];
		Assertions.assertEquals(generatedBox.getChildren().length, 2);
		Assertions.assertEquals(generatedBox.getChildren()[0], child2);
		Assertions.assertEquals(generatedBox.getChildren()[1], child3);
	}*/
	
}
