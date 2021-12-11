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
	 * Tabela de s�mbolos
	 */
	private SymbolTable symbolTable = new SymbolTable();
	
	/**
	 * Mostra se o Parser identificou uma vari�vel, �til para atribui��o de valores
	 */
	private boolean foundVar = false;
	
	/**
	 * Mostra se o Parser est� identificando uma express�o de atribui��o para uma vari�vel
	 */
	private boolean attExpression = false;
	
	/**
	 * Construtor que inicia a an�lise sint�tica
	 * @param lex
	 */
	public Parser(Lexical lex) {
		setLex(lex);
		parse();
		System.out.println(getSymbolTable());
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
			SyntaxException.invalidTerm(token, getLine());
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
					SyntaxException.invalidTerm(token, getLine());
				}
				expressionI();
			}
			term();
		}
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
		Token token = getLex().nextToken();
		verifyScope(token);
		verifySymbol(token);
		verifyExpression(token);
		buildExpression(token);
		return token;
	}
	
	/**
	 * Verifica o escopo atual do programa, informando-o para a tabela de s�mbolos
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
	 * Verifica se a vari�vel se encaixa como um s�mbolo, se for o caso, a tabela de s�mbolos o adotar� com as devidas tratativas
	 * @param token
	 */
	public void verifySymbol(Token token) {
		if (token.getType() == TokenType.VAR) {
			getSymbolTable().newSymbol(token, getLine());
			setFoundVar(true);
		}
	}
	
	/**
	 * Verifica se o parser encontrou o in�cio de uma express�o de atribui��o
	 * @param token
	 */
	public void verifyExpression(Token token) {
		if (isFoundVar() && isOperator(token)) {
			setAttExpression(true);
		}
	    if (isAttExpression() && isPunctuation(token)) {
			setAttExpression(false);
			getSymbolTable().finishExpression();
		}
	}
	
	/**
	 * Se for o caso, adiciona os tokens � express�o de atribui��o
	 * @param token
	 */
	public void buildExpression(Token token) {
		if (isAttExpression()) {
			getSymbolTable().addToExpression(token.getContent());
		}
	}
	
	/**
	 * Verifica se o token atual inicia um bloco de instru��es (escopo)
	 * @param token
	 * @return
	 */
	public boolean isStartScope(Token token) {
		if (isCommand(token))
			return true;
		return false;
	}
	
	/**
	 * Verifica se o token atual encerra um bloco de instru��es (escopo)
	 * @param token
	 * @return
	 */
	public boolean isEndScope(Token token) {
		if (token.getContent().equals("end"))
			return true;
		return false;
	}
	
	/**
	 * Verifica se o token � um comando em espec�fico
	 * @param token
	 * @return
	 */
	public boolean isCommand(Token token) {
		return token.getContent().equals("if") || token.getContent().equals("else") || token.getContent().equals("while") ||
			   token.getContent().equals("do") || token.getContent().equals("for")  || token.getContent().equals("program");
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
	 * Retorna a an�lise l�xica para o parser
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
	 * Define a an�lise l�xica para o parser
	 * @param lex
	 */
	public void setLex(Lexical lex) {
		this.lex = lex;
	}

	/**
	 * Retorna a classe respons�vel pela tabela de s�mbolos
	 * @return
	 */
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}
	
	/**
	 * Define a classe estrutura da tabela de s�mbolos
	 * @param symbolTable
	 */
	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	/**
	 * Retorna se o Parser encontrou uma vari�vel
	 * @return
	 */
	public boolean isFoundVar() {
		return foundVar;
	}

	/**
	 * Define se o Parser encontrou uma vari�vel
	 * @param isVar
	 */
	public void setFoundVar(boolean foundVar) {
		this.foundVar = foundVar;
	}

	/**
	 * Retorna se o Parser est� identificando uma express�o de atribui��o
	 * @return
	 */
	public boolean isAttExpression() {
		return attExpression;
	}

	/**
	 * Define se o Parser est� ou n�o identificando uma express�o de atribui��o
	 * @return
	 */
	public void setAttExpression(boolean attExpression) {
		this.attExpression = attExpression;
	}


}