/**
 * Tipos dos Tokens do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class TokenType {
	
	public static final byte PROGRAM 	  = 0;
	public static final byte BEGIN 	      = 1;
	public static final byte END 	      = 2;
	public static final byte INTEGER      = 3;
	public static final byte REAL         = 4;
	public static final byte LITERAL      = 5;
	public static final byte VAR          = 6;
	public static final byte KEYWORD      = 7;
	public static final byte OPERATOR 	  = 8;
	public static final byte ASS_OPERATOR = 9;
	public static final byte MAT_OPERATOR = 11;
	public static final byte LOG_OPERATOR = 12;
	public static final byte UN_OPERATOR  = 13;
	public static final byte PUNCTUATION  = 14;
	public static final byte UNDEFINED    = 15;
	public static final String[] type 	 = {
											"PROGRAM"			 , 
											"BEGIN"				 , 
											"END"				 , 
											"INTEGER"			 , 
											"REAL"				 , 
											"LITERAL"			 , 
											"ID"				 , 
											"KEYWORD"			 , 
											"OPERATOR"			 ,
											"ASSIGNMENT OPERADOR",
											null				 , 
											"MATH OPERATOR"		 , 
											"LOGICAL OPERATOR"	 ,
											"UNARY OPERATOR" 	 ,
											"PUNCTUATION"		 ,
											"UNDEFINED"
											};
	
	
}
