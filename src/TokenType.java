/**
 * Tipos dos Tokens do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class TokenType {
	
	/**
	 * Tipo do token:
	 * -N�mero inteiro
	 * -N�mero real
	 * -Literal
	 * -Vari�vel
	 * -Palavra chave
	 * -Operador gen�rico
	 * -Operador de atribui��o
	 * -Operador matem�tico
	 * -Operador l�gico
	 * -Operador un�rio
	 * -Pontua��o
	 * -Indefinido
	 */
	public static final byte INTEGER      = 0;
	public static final byte REAL         = 1;
	public static final byte LITERAL      = 2;
	public static final byte VAR          = 3;
	public static final byte KEYWORD      = 4;
	public static final byte OPERATOR 	  = 5;
	public static final byte ASS_OPERATOR = 6;
	public static final byte MAT_OPERATOR = 7;
	public static final byte LOG_OPERATOR = 8;
	public static final byte UN_OPERATOR  = 9;
	public static final byte PUNCTUATION  = 11;
	public static final byte UNDEFINED    = 12;
	public static final String[] type 	 = {
											"INTEGER"			 , 
											"REAL"				 , 
											"LITERAL"			 , 
											"ID"				 , 
											"KEYWORD"			 , 
											"OPERATOR"			 ,
											"ASSIGNMENT OPERADOR",
											"MATH OPERATOR"		 , 
											"LOGICAL OPERATOR"	 ,
											"UNARY OPERATOR" 	 ,
											null				 ,
											"PUNCTUATION"		 ,
											"UNDEFINED"
											};
	
	
}