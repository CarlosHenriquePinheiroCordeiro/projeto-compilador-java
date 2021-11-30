/**
 * Tipos dos Tokens do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class TokenType {
	
	public static final int INTEGER      = 0;
	public static final int REAL         = 1;
	public static final int LITERAL      = 2;
	public static final int VAR          = 3;
	public static final int KEYWORD      = 4;
	public static final int OPERATOR 	 = 5;
	public static final int ATT_OPERATOR = 6;
	public static final int MAT_OPERATOR = 7;
	public static final int LOG_OPERATOR = 8;
	public static final int PUNCTUATION  = 9;
	public static final int UNDEFINED    = 999;
	public static final String[] type 	 = {"INTEGER", "REAL", "LITERAL", "ID", "KEYWORD", "OPERATOR",
											"ASSIGNMENT OPERADOR", "MATH OPERATOR", "LOGICAL OPERATOR", "PUNCTUATION"};
	
	
}
