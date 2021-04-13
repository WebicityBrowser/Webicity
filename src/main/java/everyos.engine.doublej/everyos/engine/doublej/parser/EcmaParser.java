package everyos.engine.doublej.parser;

import java.util.ArrayList;

public class EcmaParser {
	protected ArrayList<EcmaParseState> paths;
	protected ArrayList<EcmaGrammar> completions;
	protected ArrayList<EcmaToken> tokens;
	protected String content;
	
	public EcmaParser(String content) {
		paths = new ArrayList<>();
		completions = new ArrayList<>();
		this.content = content;
		
		@SuppressWarnings("unused")
		String punctuators = "{ ( ) [ ] . ... ; , < > <= >= == != === !== + - * % ** ++ -- << >> >>> & | ^ ! ~ && || ?? ? : = += -= *= %= **= <<= >>= >>>= &= |= ^= =>";
		
		EcmaGrammar goal = new EcmaScriptGrammar().parent(new EcmaGrammarCompletion());
		
		paths.add(new EcmaParseState(null, goal, 0));
	}
	
	public void lex() {
		tokens = new ArrayList<EcmaToken>();
		int i = 0; int l = content.length();
		while (i<l) {
			char ch = content.charAt(i);
			if ((" \t\u000B\u000C\u00A0\uFEFF").indexOf(ch)!=-1||Character.getType(ch)==Character.SPACE_SEPARATOR) {
				i++;
			} else if (("\n\r\u2028\u2029").indexOf(ch)!=-1) {
				i++;
				if (ch=='\n'&&i<l&&content.charAt(i)=='\r') i++;
				tokens.add(new EcmaLineTerminatorToken());
			} else if (ch=='/'&&i+1<l&&content.charAt(i+1)=='/') {
				i+=2;
				while (i<l&&("\n\r\u2028\u2029").indexOf(content.charAt(i))!=-1) i++;
			} else if (ch=='/'&&i+1<l&&content.charAt(i+1)=='*') {
				i+=2;
				while (i<l+1&&!content.substring(i, i+2).equals("*/")) i++;
				//TODO: Error
			} else if (Character.isJavaIdentifierStart(ch)) { //TODO: Handle unicode escape sequences
				StringBuilder name = new StringBuilder();
				name.append(ch);
				i++;
				char np;
				while (i<l&&Character.isJavaIdentifierPart(np=content.charAt(i))) {
					name.append(np);
					i++;
				}
				System.out.println(name.toString());
				tokens.add(new EcmaIdentifierToken(name.toString()));
			} else {
				//Error
				//System.out.println("NO");
			}
		}
	}
	
	public void parse() {
		//TODO: For now I'll just use a while statement, to avoid complexity
		//Later, I should split this across more methods for custom flow control
		lex();
		while (paths.size()>0/*&&completions.size()<1*/) {
			EcmaParseState path = paths.get(0);
			paths.remove(0);
			EcmaGrammar grammar = path.incoming;
			path.incoming = grammar.parent;
			grammar.parent = path.finished;
			path.finished = grammar;
			System.out.println(paths.size()+""+grammar);
			grammar.parse(this, path);
		}
		System.out.println(completions.size());
	}
}