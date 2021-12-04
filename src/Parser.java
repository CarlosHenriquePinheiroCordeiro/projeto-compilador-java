/**
 * Analisador Sint�tico (Parser) do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Parser {
	
	/**
	 * Analisador L�xico para o Parser (Analisador Sint�tico)
	 */
	private Lexical lex;
	
	/**
	 * Construtor que inicia a an�lise sint�tica
	 * @param lex
	 */
	public Parser(Lexical lex) {
		setLex(lex);
		parse();
	}
	
	/**
	 * In�cio da an�lise sint�tica
	 */
	public void parse() {
		start();
	}
	
	/**
	 * Representa o ponto de partida do programa, devendo come�ar com um termo
	 */
	public void start() {
		Token token = nextToken();
		if (!isTerm(token)) {
			SyntaxException.invalidTerm(token, getLex().getLine());
		}
		expression();
	}
	
	/**
	 * Representando o 'expression', define a base de uma express�o do programa
	 */
	public void expression() {
		term();
		expressionI();
	}
	
	/**
	 * Representando o 'term', define o termo de uma express�o, sendo uma vari�vel, um n�mero ou uma palavra chave
	 */
	public void term() {
		Token token = nextToken();
		if (!isFinish(token)) {
			if (!isPunctuation(token)) {
				expressionI();
			}
		}
	}
	
	/**
	 * Representando o 'expressionI', analisa e monta a express�o como um todo para o programa, 
	 * de forma recursiva, continuando ou n�o a express�o
	 */
	public void expressionI() {
		Token token = nextToken();
		if (!isFinish(token)) {
			if (isOperator(token)) {
				token = nextToken();
				if (isOperator(token)) {
					SyntaxException.invalidTerm(token, getLex().getLine());
				}
				expressionI();
			}
			term();
		}
	}
	
	/**
	 * Retorna se o token � um termo, sendo este uma palavra chave, vari�vel, um n�mero ou uma literal
	 * @param token
	 * @return
	 */
	public boolean isTerm(Token token) {
		return isKeyword(token) || isVar(token) || isNumber(token) || isLiteral(token);
	}
	
	/**
	 * Retorna se o token � uma palavra reservada, como por exemplo 'if', 'var' ou 'while'
	 * @param token
	 * @return
	 */
	public boolean isKeyword(Token token) {
		return token.getType() == TokenType.KEYWORD;
	}
	
	/**
	 * Retorna se o token � um n�mero real ou inteiro
	 * @param token
	 * @return
	 */
	public boolean isNumber(Token token) {
		return token.getContent().matches("[0-9]{1,}.[0-9]{1,}") || token.getContent().matches("[0-9]{1,}");
	}
	
	/**
	 * Retora se o token � um tipo de operador, seja aritm�tico, l�gico ou de atribui��o
	 * @param token
	 * @return
	 */
	public boolean isOperator(Token token) {
		return getLex().getMathOperators().contains(token.getContent()) || 
			   getLex().getLogOperators().contains(token.getContent())  ||
			   getLex().getAssOperators().contains(token.getContent());
	}
	
	/**
	 * Retora se o token � um operador un�rio
	 * @param token
	 * @return
	 */
	public boolean isUnaryOperator(Token token) {
		return getLex().getUnOperators().contains(token.getContent());
	}
	
	/**
	 * Retorna se o token � uma vari�vel
	 * @param token
	 * @return
	 */
	public boolean isVar(Token token) {
		return token.getType() == TokenType.VAR;
	}
	
	/**
	 * Retorna se o token � uma literal
	 * @param token
	 * @return
	 */
	public boolean isLiteral(Token token) {
		return token.getType() == TokenType.LITERAL;
	}
	
	/**
	 * Retorna se o token � um ponto e v�rgula
	 * @param token
	 * @return
	 */
	public boolean isPunctuation(Token token) {
		return token.getType() == TokenType.PUNCTUATION;
	}
	
	/**
	 * Retorna se o token � nulo, ou seja, o programa chegou ao fim
	 * @param token
	 * @return
	 */
	public boolean isFinish(Token token) {
		return token == null;
	}
	
	/**
	 * Retorna o pr�ximo token vindo da an�lise l�xica para a an�lise sint�tica
	 * @param token
	 * @return
	 */
	public Token nextToken() {
		if (getLex().isFinish()) {
			return null;
		}
		return getLex().newToken();
	}
	
	/**
	 * Retorna a an�lise l�xica para o parser
	 * @return
	 */
	public Lexical getLex() {
		return lex;
	}

	/**
	 * Define a an�lise l�xica para o parser
	 * @param lex
	 */
	public void setLex(Lexical lex) {
		this.lex = lex;
	}
	
	
}
