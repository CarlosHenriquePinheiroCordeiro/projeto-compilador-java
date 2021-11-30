/**
 * Classe de exceções do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class LexicalException {

	public static void invalidSymbol(char symbol, int line) {
		throw new RuntimeException("Lexical Error: Invalid symbol found ("+symbol+")."+getLine(line));
	}
	
	public static void underlineFinish(int line) {
		throw new RuntimeException("Lexical Error: Words cannot end with an underline (_)."+getLine(line));
	}
	
	public static void realPoints(int line) {
		throw new RuntimeException("Lexical Error: Invalid real numbers punctuation."+getLine(line));
	}
	
	public static void invalidNumber(String number, int line) {
		throw new RuntimeException("Lexical Error: Invalid number ("+number+") found."+getLine(line));
	}
	
	public static void invalidLiteral(String literal, int line) {
		throw new RuntimeException("Lexical Error: Invalid literal found ("+literal+")."+getLine(line));
	}
	
	public static void invalidOperator(String operator, int line) {
		throw new RuntimeException("Lexical Error: Invalid operator found ("+operator+")."+getLine(line));
	}
	
	private static String getLine(int line) {
		return " \nLine "+line;
	}
	
	
}
