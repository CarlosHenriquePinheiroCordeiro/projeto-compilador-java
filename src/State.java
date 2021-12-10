/**
 * Estados do analisador léxico
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class State {

	/**
	 * Estados:
	 * -Inicial
	 * -Número
	 * -Palavra
	 * -Literal
	 * -Fim do token atual sendo analisado
	 * -Fim do programa
	 * -Operador
	 * -Pontuação
	 */
	public static final int INITIAL     = 0;
	public static final int NUMBER      = 10;
	public static final int WORD        = 20;
	public static final int LITERAL     = 30;
	public static final int TOKEN_END   = 40;
	public static final int PROGRAM_END = 50;
	public static final int OPERATOR 	= 60;
	public static final int PUNCTUATION = 70;
	
	
}