package everyos.browser.jcss.cssom;

import java.util.ArrayList;
import java.util.List;

public class CSSOMNode {
	private List<CSSOMNode> nodes = new ArrayList<>(1);
	
	public void addNode(CSSOMNode node) {
		nodes.add(node);
	}
	
	public CSSOMNode[] getNodes() {
		return nodes.toArray(new CSSOMNode[nodes.size()]);
	}
}
