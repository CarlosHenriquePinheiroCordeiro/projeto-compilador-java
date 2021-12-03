/**
 * Analisador Sintático (Parser) do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Parser {
	
	private Lexical lex;
	
	public Parser(Lexical lex) {
		setLex(lex);
		parse();
	}
	
	/**
	 * Início da análise sintática
	 */
	public void parse() {
		start();
	}
	
	public void start() {
		Token token = nextToken();
		if (!isTerm(token)) {
			SyntaxException.invalidTerm(token, getLex().getLine());
		}
		System.out.println(token.getContent());
		expression();
	}
	
	/**
	 * Representando o 'expression', define a base de uma expressão do programa
	 */
	public void expression() {
		term();
		expressionI();
	}
	
	/**
	 * Representando o 'term', define o termo de uma expressão, sendo uma variável ou um número
	 */
	public void term() {
		Token token = nextToken();
		if (!isFinish(token)) {
			if (!isPunctuation(token)) {
				System.out.println(token.getContent());
				expressionI();
			}
		}
	}
	
	/**
	 * Representando o 'expressionI', analisa e monta a expressão como um todo para o programa, 
	 * de forma recursiva, continuando ou não a expressão
	 */
	public void expressionI() {
		Token token = nextToken();
		if (!isFinish(token)) {
			System.out.println(token.getContent());
			if (isOperator(token) && !isUnaryOperator(token)) {
				expressionI();
			}
			term();
		}
	}
	
	public void punctuation() {
		Token token = nextToken();
		if (!isFinish(token)) {
			if (!isPunctuation(token)) {
				SyntaxException.invalidPunctuation(token, getLex().getLine());
			}
			System.out.println(token.getContent());
			expression();
		}
	}
	
	/**
	 * Retorna se o token é um termo, sendo este uma variável, um número ou uma literal
	 * @param token
	 * @return
	 */
	public boolean isTerm(Token token) {
		return isKeyword(token) || isVar(token) || isNumber(token) || isLiteral(token);
	}
	
	/**
	 * Retorna se o token é uma palavra reservada, como por exemplo 'if', 'var' ou 'while'
	 * @param token
	 * @return
	 */
	public boolean isKeyword(Token token) {
		return token.getType() == TokenType.KEYWORD;
	}
	
	/**
	 * Retorna se o token é um número real ou inteiro
	 * @param token
	 * @return
	 */
	public boolean isNumber(Token token) {
		return token.getContent().matches("[0-9]{1,}.[0-9]{1,}") || token.getContent().matches("[0-9]{1,}");
	}
	
	/**
	 * Retora se o token é um tipo de operador, seja aritmético, lógico ou de atribuição
	 * @param token
	 * @return
	 */
	public boolean isOperator(Token token) {
		return getLex().getMathOperators().contains(token.getContent()) || 
			   getLex().getLogOperators().contains(token.getContent())  ||
			   getLex().getAssOperators().contains(token.getContent());
	}
	
	/**
	 * Retora se o token é um operador unário
	 * @param token
	 * @return
	 */
	public boolean isUnaryOperator(Token token) {
		return getLex().getUnOperators().contains(token.getContent());
	}
	
	/**
	 * Retorna se o token é uma variável
	 * @param token
	 * @return
	 */
	public boolean isVar(Token token) {
		return token.getType() == TokenType.VAR;
	}
	
	/**
	 * Retorna se o token é uma literal
	 * @param token
	 * @return
	 */
	public boolean isLiteral(Token token) {
		return token.getType() == TokenType.LITERAL;
	}
	
	/**
	 * Retorna se o token é um ponto e vírgula
	 * @param token
	 * @return
	 */
	public boolean isPunctuation(Token token) {
		return token.getType() == TokenType.PUNCTUATION;
	}
	
	/**
	 * Retorna se o token é nulo, ou seja, o programa chegou ao fim
	 * @param token
	 * @return
	 */
	public boolean isFinish(Token token) {
		return token == null;
	}
	
	/**
	 * Retorna o próximo token vindo da análise léxica para a análise sintática
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
	 * Retorna a análise léxica para o parser
	 * @return
	 */
	public Lexical getLex() {
		return lex;
	}

	/**
	 * Define a análise léxica para o parser
	 * @param lex
	 */
	public void setLex(Lexical lex) {
		this.lex = lex;
	}
	
	
}
