/**
 * Classe de exceções da análise sintática
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class SyntaxException {

	public static void invalidProgram(Token token, int line) {
		throw new RuntimeException(getError()+"Program definition ('program') expected at the document beginning. \'"+token.getContent()+"\' ("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	public static void invalidStart(Token token, int line) {
		throw new RuntimeException(getError()+"Initial symbol ('begin') expected at the code beginning. \'"+token.getContent()+"\' ("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	public static void invalidEnd(Token token, int line) {
		throw new RuntimeException(getError()+"Expected end token ('end'). \'"+token.getContent()+"\' ("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	private static String getError() {
		return "Syntax Error: ";
	}
	
	private static String getLine(int line) {
		return " \nLine: "+line;
	}
	
	
}
