/**
 * Classe de exceções da análise sintática
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class SyntaxException {
	
	public static void invalidTerm(Token token, int line) {
		throw new RuntimeException(getError()+"Expected var or number. \'"+token.getContent()+"\'("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	public static void invalidOperator(Token token, int line) {
		throw new RuntimeException(getError()+"Expected id or keyword. \'"+token.getContent()+"\'("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	public static void invalidPunctuation(Token token, int line) {
		throw new RuntimeException(getError()+"Expected ';'. \'"+token.getContent()+"\'("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	private static String getError() {
		return "Syntax Error: ";
	}
	
	private static String getLine(int line) {
		return " \nLine: "+line;
	}
	
	
}
