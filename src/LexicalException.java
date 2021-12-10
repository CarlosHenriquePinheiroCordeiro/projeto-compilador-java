/**
 * Classe de exce��es da an�lise l�xica
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class LexicalException {

	/**
	 * Erro de s�mbolo inv�lido
	 * @param symbol
	 * @param line
	 */
	public static void invalidSymbol(char symbol, int line) {
		throw new RuntimeException("Lexical Error: Invalid symbol found ("+symbol+")."+getLine(line));
	}
	
	/**
	 * Erro de palavra encerrando com 'underline'
	 * @param line
	 */
	public static void underlineFinish(int line) {
		throw new RuntimeException("Lexical Error: Words cannot end with an underline (_)."+getLine(line));
	}
	
	/**
	 * Erro referente ao n�mero irregular de pontos para a defini��o de n�meros reais, exemplo: 3.14.2333.2
	 * @param line
	 */
	public static void realPoints(int line) {
		throw new RuntimeException("Lexical Error: Invalid real numbers punctuation."+getLine(line));
	}
	
	/**
	 * Erro de n�mero inv�lido informado, exemplo: 12abc23
	 * @param number
	 * @param line
	 */
	public static void invalidNumber(String number, int line) {
		throw new RuntimeException("Lexical Error: Invalid number ("+number+") found."+getLine(line));
	}
	
	/**
	 * Erro referente a uma literal inv�lida informada, exemplo: "Carlos Henrique
	 * @param literal
	 * @param line
	 */
	public static void invalidLiteral(String literal, int line) {
		throw new RuntimeException("Lexical Error: Invalid literal found ("+literal+")."+getLine(line));
	}
	
	/**
	 * Erro referente a um operador inv�lido. Exemplo: +12abc
	 * @param operator
	 * @param line
	 */
	public static void invalidOperator(String operator, int line) {
		throw new RuntimeException("Lexical Error: Invalid operator found ("+operator+")."+getLine(line));
	}
	
	/**
	 * M�todo �til para retornar a linha do erro
	 * @param line
	 * @return
	 */
	private static String getLine(int line) {
		return " \nLine "+line;
	}
	
	
}