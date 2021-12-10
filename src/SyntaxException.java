/**
 * Classe de exceções da análise sintática
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class SyntaxException {
	
	/**
	 * Erro referente à situação onde era esperado um termo
	 * @param token
	 * @param line
	 */
	public static void invalidTerm(Token token, int line) {
		throw new RuntimeException(getError()+"Expected var or number. \'"+token.getContent()+"\'("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	/**
	 * Erro referente à situação onde era esperado um operador
	 * @param token
	 * @param line
	 */
	public static void invalidOperator(Token token, int line) {
		throw new RuntimeException(getError()+"Expected id or keyword. \'"+token.getContent()+"\'("+TokenType.type[token.getType()]+") given."+getLine(line));
	}
	
	/**
	 * Método útil para print do erro
	 * @return
	 */
	private static String getError() {
		return "Syntax Error: ";
	}
	
	/**
	 * Método útil para print do erro
	 * @param line
	 * @return
	 */
	private static String getLine(int line) {
		return " \nLine: "+line;
	}
	
	
}
