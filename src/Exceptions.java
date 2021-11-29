/**
 * Classe de exce��es do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Exceptions {

	public static void lexicalInvalidSymbol(char symbol, int line) {
		throw new RuntimeException("S�mbolo inv�lido encontrado ("+symbol+") na linha "+line);
	}
	
	public static void lexicalUnderlineFinish(int line) {
		throw new RuntimeException("Linha "+line+". Palavras n�o podem encerrar com underline (_)");
	}
	
	public static void lexicalRealPoints(int line) {
		throw new RuntimeException("Linha "+line+". N�meros reais podem ter somente um separador decimal (.)");
	}
	
	public static void lexicalInvalidNumber(String number, int line) {
		throw new RuntimeException("N�mero inv�lido encontrado ("+number+") na linha "+line);
	}
	
	public static void lexicalInvalidLiteral(String literal, int line) {
		throw new RuntimeException("Literal inv�lida encontrada ("+literal+") na linha "+line);
	}
	
	public static void lexicalInvalidOperator(String operator, int line) {
		throw new RuntimeException("Operador inv�lido encontrado ("+operator+") na linha "+line);
	}
	
	
}
