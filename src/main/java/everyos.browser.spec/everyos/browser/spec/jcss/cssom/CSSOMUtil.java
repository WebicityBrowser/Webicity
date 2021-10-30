package everyos.browser.spec.jcss.cssom;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.jcss.cssom.property.Property;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelector;
import everyos.browser.spec.jcss.cssom.selector.ComplexSelectorPart;
import everyos.browser.spec.jcss.imp.QualifiedRule;
import everyos.browser.spec.jcss.imp.SimpleBlock;
import everyos.browser.spec.jcss.intf.CSSRule;
import everyos.browser.spec.jcss.parser.Declaration;
import everyos.browser.spec.jcss.parser.JCSSParser;
import everyos.browser.spec.jcss.parser.Tuple;

//TODO: Track whether attributes are from UA or Site
public class CSSOMUtil {
	public static CSSOMNode computeCSSOM(List<CSSRule[]> cssRules) {
		CSSOMNode rootCSSOMNode = new CSSOMNode();
		
		for (CSSRule[] ruleSet: cssRules) {
			mergeCSSOM(rootCSSOMNode, ruleSet);
		}
		
		return rootCSSOMNode;
	}

	private static void mergeCSSOM(CSSOMNode root, CSSRule[] ruleSet) {
		for (CSSRule rule: ruleSet) {
			if (rule instanceof QualifiedRule) {
				mergeCSSOM(root, (QualifiedRule) rule);
			}
			//TODO: Handle at-rule
		}
	}

	private static void mergeCSSOM(CSSOMNode root, QualifiedRule rule) {
		Object[] prelude = rule.getPrelude();
		int[] i = new int[1];
		while (i[0] < prelude.length) {
			ComplexSelector selector = parseSelector(prelude, i);
			if (selector != null) {
				mergeCSSOM(root, selector, rule.getBlock());
			}
		}
	}

	private static ComplexSelector parseSelector(Object[] prelude, int[] offset) {
		return new SelectorParser().parse(prelude, offset);
	}

	private static void mergeCSSOM(CSSOMNode root, ComplexSelector selector, Object block) {
		CSSOMNode current = root;
		
		ComplexSelectorPart[] selectorParts = selector.getParts();
		
		for (int i = selectorParts.length - 1; i >= 0; i--) {
			current = current.getOrCreateNode(selectorParts[i]);
		}
		
		//TODO: PsuedoSelector
		
		if (block instanceof SimpleBlock) {
			mergeBlock(current, (SimpleBlock) block);
		}
	}

	private static void mergeBlock(CSSOMNode node, SimpleBlock block) {
		CSSRule[] rules = JCSSParser.parseAListOfDeclarations(block.getValue());
		for (CSSRule rule: rules) {
			if (!(rule instanceof Declaration)) {
				continue;
			}
			
			Property property = DeclarationParser.getPropertyFor((Declaration) rule);
			if (property != null) {
				node.setProperty(property);
			}
		}
	}
	
	public static CSSOMNode[] getMatchingNodes(Node baseDomNode, CSSOMNode root) {
		List<CSSOMNode> fin = new ArrayList<>(2);
		ArrayDeque<Tuple<CSSOMNode, Node>> next = new ArrayDeque<>();
		
		next.add(new Tuple<>(root, baseDomNode));
		while (!next.isEmpty()) {
			Tuple<CSSOMNode, Node> nodes = next.pop();
			CSSOMNode cssNode = nodes.getT1();
			Node domNode = nodes.getT2();
			
			fin.add(cssNode);
			
			for (ComplexSelectorPart selector: cssNode.getSelectors(domNode)) {
				if (selector == null) {
					continue;
				}
				
				Node[] matches = selector.matches(domNode);
				CSSOMNode matched = cssNode.getNode(selector);
				
				for (Node match: matches) {
					next.add(new Tuple<>(matched, match));
				}
			}
		}
		
		return fin.toArray(new CSSOMNode[fin.size()]);
	}
	
	//TODO: This is only a rough, inefficient implementation
	public static ApplicablePropertyMap calculateProperties(CSSOMNode[] cssomNodes) {
		HashMap<PropertyName, Property> properties = new HashMap<>(8);
		
		for (CSSOMNode cssomNode: cssomNodes) {
			for (Property property: cssomNode.getProperties()) {
				properties.put(property.getPropertyName(), property);
			}
		}
		
		return new ApplicablePropertyMap(properties);
	}
	
}
