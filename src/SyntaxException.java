
public class SyntaxException {

	public static void invalidProgram(Token token, int line) {
		throw new RuntimeException("Syntax Error: Program definition ('program') expected at the document beginning. \'"+token.getContent()+"\' ("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	public static void invalidStart(Token token, int line) {
		throw new RuntimeException("Syntax Error: Initial symbol ('begin') expected at the code beginning. \'"+token.getContent()+"\' ("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	private static String getLine(int line) {
		return " \nLine: "+line;
	}
	
	
}
