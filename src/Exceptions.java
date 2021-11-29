/**
 * Classe de exceções do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Exceptions {

	public static void lexicalInvalidSymbol(char symbol, int line) {
		throw new RuntimeException("Símbolo inválido encontrado ("+symbol+") na linha "+line);
	}
	
	public static void lexicalUnderlineFinish(int line) {
		throw new RuntimeException("Linha "+line+". Palavras não podem encerrar com underline (_)");
	}
	
	public static void lexicalRealPoints(int line) {
		throw new RuntimeException("Linha "+line+". Números reais podem ter somente um separador decimal (.)");
	}
	
	public static void lexicalInvalidNumber(String number, int line) {
		throw new RuntimeException("Número inválido encontrado ("+number+") na linha "+line);
	}
	
	public static void lexicalInvalidLiteral(String literal, int line) {
		throw new RuntimeException("Literal inválida encontrada ("+literal+") na linha "+line);
	}
	
	public static void lexicalInvalidOperator(String operator, int line) {
		throw new RuntimeException("Operador inválido encontrado ("+operator+") na linha "+line);
	}
	
	
}
