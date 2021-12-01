/**
 * Analisador Sintático (Parser) do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Parser {
	
	private Lexical lex;
	
	public Parser(Lexical lex) {
		setLex(lex);
	}
	
	/**
	 * Início da análise sintática
	 * @return
	 */
	public boolean parse() {
		program();
		return true;
	}

	/**
	 * Representando o 'program', analisa o início do programa e se este começa com 'program', definindo o programa em si
	 */
	public void program() {
		Token token = nextToken();
		if (!isProgram(token)) {
			SyntaxException.invalidProgram(token, getLex().getLine());
		}
		begin();
	}
	
	/**
	 * Representando o 'begin', define começo de um bloco de instruções, dando continuidade chamando o 'statement_list' e o 'end'
	 */
	public void begin() {
		Token token = nextToken();
		if (!isBegin(token)) {
			SyntaxException.invalidStart(token, getLex().getLine());
		}
		statementList();
		end();
	}
	
	/**
	 * Representando o 'statement_list', chama os devidos blocos de início ('begin'), instruções ('statement') e fim ('end')
	 */
	public void statementList() {
		Token token = nextToken();
		if (isBegin(token)) {
			begin();
		}
		statement();
		if (isEnd(token)) {
			end();
		}
	}
	
	/**
	 * Representando o 'statement', define uma instrução do programa
	 */
	public void statement() {
		expression();
		Token token = nextToken();
		if (isPunctuation(token)) {
			statementList();
		}
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
		if (!isTerm(token)) {
			SyntaxException.invalidTerm(token, getLex().getLine());
		}
	}
	
	/**
	 * Representando o 'expressionI', analisa e monta a expressão como um todo para o programa, 
	 * de forma recursiva, continuando ou não a expressão
	 */
	public void expressionI() {
		Token token = nextToken();
		if (isOperator(token)) {
			term();
			expressionI();
		}
	}
	
	/**
	 * Representando o 'end', define um fim de bloco de instruções do programa
	 */
	public void end() {
		Token token = nextToken();
		if (!isEnd(token)) {
			SyntaxException.invalidEnd(token, getLex().getLine());
		}
	}
	
	/**
	 * Retorna se o token é um 'program'
	 * @param token
	 * @return
	 */
	public boolean isProgram(Token token) {
		return token.getType() == TokenType.PROGRAM;
	}
	
	/**
	 * Retorna se o token é um 'begin'
	 * @param token
	 * @return
	 */
	public boolean isBegin(Token token) {
		return token.getType() == TokenType.BEGIN;
	}
	
	/**
	 * Retorna se o token é um 'end'
	 * @param token
	 * @return
	 */
	public boolean isEnd(Token token) {
		return token.getType() == TokenType.END;
	}
	
	/**
	 * Retorna se o token é um termo, sendo este uma variável, um número ou uma literal
	 * @param token
	 * @return
	 */
	public boolean isTerm(Token token) {
		return isVar(token) || isNumber(token) || isLiteral(token);
	}
	
	/**
	 * Retorna se o token é uma palavra reservada, como por exemplo 'if', 'var' ou 'while'
	 * @param token
	 * @return
	 */
	public boolean isKeyWord(Token token) {
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
	 * Retorna o próximo token vindo da análise léxica para a análise sintática
	 * @param token
	 * @return
	 */
	public Token nextToken() {
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
