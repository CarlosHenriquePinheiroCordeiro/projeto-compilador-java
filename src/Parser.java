/**
 * Analisador Sintático (Parser) do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Parser {
	
	/**
	 * Analisador Léxico para o Parser (Analisador Sintático)
	 */
	private Lexical lex;
	
	/**
	 * Tabela de símbolos
	 */
	private SymbolTable symbolTable = new SymbolTable();
	
	/**
	 * Construtor que inicia a análise sintática
	 * @param lex
	 */
	public Parser(Lexical lex) {
		setLex(lex);
		parse();
		System.out.println(getSymbolTable());
	}
	
	/**
	 * Início da análise sintática
	 */
	public void parse() {
		start();
	}
	
	/**
	 * Representa o ponto de partida do programa, devendo começar com um termo
	 */
	public void start() {
		Token token = nextToken();
		if (!isTerm(token)) {
			SyntaxException.invalidTerm(token, getLine());
		}
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
	 * Representando o 'term', define o termo de uma expressão, sendo uma variável, um número ou uma palavra chave
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
	 * Representando o 'expressionI', analisa e monta a expressão como um todo para o programa, 
	 * de forma recursiva, continuando ou não a expressão
	 */
	public void expressionI() {
		Token token = nextToken();
		if (!isFinish(token)) {
			if (isOperator(token)) {
				token = nextToken();
				if (isOperator(token)) {
					SyntaxException.invalidTerm(token, getLine());
				}
				expressionI();
			}
			term();
		}
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
		Token token = getLex().nextToken();
		verifyScope(token);
		verifySymbol(token);
		return token;
	}
	
	/**
	 * Verifica o escopo atual do programa, informando-o para a tabela de símbolos
	 * @param token
	 */
	public void verifyScope(Token token) {
		if (isKeyword(token)) {
			if (isStartScope(token)) {
				getSymbolTable().plusActualScope();
			}
			else if (isEndScope(token)) {
				getSymbolTable().subtractActualScope();
			}
		}
	}
	
	/**
	 * Verifica se a variável se encaixa como um símbolo, se for o caso, a tabela de símbolos o adotará com as devidas tratativas
	 * @param token
	 */
	public void verifySymbol(Token token) {
		if (token.getType() == TokenType.VAR) {
			getSymbolTable().newSymbol(token, getLine());
		}
	}
	
	/**
	 * Verifica se o token atual inicia um bloco de instruções (escopo)
	 * @param token
	 * @return
	 */
	public boolean isStartScope(Token token) {
		if (isCommand(token))
			return true;
		return false;
	}
	
	/**
	 * Verifica se o token atual encerra um bloco de instruções (escopo)
	 * @param token
	 * @return
	 */
	public boolean isEndScope(Token token) {
		if (token.getContent().equals("end"))
			return true;
		return false;
	}
	
	/**
	 * Verifica se o token é um comando em específico
	 * @param token
	 * @return
	 */
	public boolean isCommand(Token token) {
		return token.getContent().equals("if") || token.getContent().equals("else") || token.getContent().equals("while") ||
			   token.getContent().equals("do") || token.getContent().equals("for")  || token.getContent().equals("program");
	}
	
	/**
	 * Retorna se o token é um termo, sendo este uma palavra chave, variável, um número ou uma literal
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
	 * Retorna a análise léxica para o parser
	 * @return
	 */
	public Lexical getLex() {
		return lex;
	}
	
	/**
	 * Retorna a linha atual do programa
	 * @return
	 */
	public int getLine() {
		return getLex().getLine();
	}

	/**
	 * Define a análise léxica para o parser
	 * @param lex
	 */
	public void setLex(Lexical lex) {
		this.lex = lex;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}


}