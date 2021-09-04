package everyos.browser.jcss.cssom;

import everyos.browser.jcss.imp.QualifiedRule;
import everyos.browser.jcss.imp.SimpleBlock;
import everyos.browser.jcss.intf.CSSRule;
import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jcss.parser.CSSToken;

//TODO: Track whether attributes are from UA or Site
public class CSSOMUtil {
	public static CSSOMNode computeCSSOM(CSSStyleSheet[] stylesheets) {
		CSSOMNode rootCSSOMNode = new CSSOMNode();
		
		for (CSSStyleSheet stylesheet: stylesheets) {
			mergeCSSOM(rootCSSOMNode, stylesheet);
		}
		
		return rootCSSOMNode;
	}

	private static void mergeCSSOM(CSSOMNode root, CSSStyleSheet stylesheet) {
		for (CSSRule rule: stylesheet.getCSSRules()) {
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
			SelectorPart[] selector = parseSelector(prelude, i);
			if (selector != null) {
				mergeCSSOM(root, selector, rule.getBlock());
			}
		}
	}

	private static SelectorPart[] parseSelector(Object[] prelude, int[] offset) {
		return new SelectorParser().parse(prelude, offset);
	}

	private static void mergeCSSOM(CSSOMNode root, SelectorPart[] selector, Object block) {
		CSSOMNode current = root;
		
		for (int i = selector.length-1; i >= 0; i--) {
			CSSOMNode next = new CSSOMNode();
			current.addNode(next);
			current = next;
		}
		
		if (block instanceof SimpleBlock) {
			mergeBlock(current, (SimpleBlock) block);
		}
	}

	private static void mergeBlock(CSSOMNode current, SimpleBlock block) {
		for (CSSToken rule: block.getValue()) {
			System.out.print("B");
			System.out.println(rule);
		}
	}
}
