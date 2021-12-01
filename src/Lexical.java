import java.util.ArrayList;
import java.util.List;

/**
 * Classe respons�vel pela an�lise l�xica do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Lexical {
	
	private char[] code;
	private List<String> keyWords  	   = new ArrayList<String>();
	private List<String> mathOperators = new ArrayList<String>();
	private List<String> assOperators  = new ArrayList<String>();
	private List<String> logOperators  = new ArrayList<String>();
	private List<String> punOperators  = new ArrayList<String>();
	private List<Token>  tokens 	   = new ArrayList<Token>();
	
	private int     position  = 0;
	private int     line 	  = 1;
	private int     sQuotes   = 0;
	private int     dQuotes   = 0;
	private int     state 	  = State.INITIAL;
	private byte    tokenType = TokenType.UNDEFINED;
	private boolean finish 	  = false;
	
	/**
	 * Construtor que instancia as palavras chave e operadores para a an�lise l�xica
	 * @param code
	 */
	public Lexical(char[] code) {
		setCode(code);
		setKeyWords();
		setMathOperators();
		setAssOperators();
		setLogOperators();
	}

	/**
	 * Retorna uma lista de tokens lexicalmente analisados com sucesso
	 * @return lista com tokens corretos
	 */
	public List<Token> analisys() {
		do {
			tokens.add(newToken());
		} while (!isProgramEnd()); //Equanto ainda houver caracteres para serem lidos
		return tokens;
	}
	
	/**
	 * Realiza a an�lise l�xica de um novo token, percorrendo todos os tokens do programa analisado a cada chamada deste m�todo
	 * @return novo token corretamente analisado
	 */
	public Token newToken() {
		String content = "";
		state 		   = State.INITIAL;
		tokenType 	   = TokenType.UNDEFINED;
		while (true) {
			if (isProgramEnd()) {
				state  = State.PROGRAM_END;
				finish = true;
			}
			else if (isTokenEnd()) {
				if (state != State.LITERAL) {
					state = State.TOKEN_END;
				}
			}
			switch (state) {
				case State.INITIAL:
					takeState();
					break;
					
				case State.OPERATOR:
					if (isCarriageReturn() || isPunctuationChar()) {
						if (isOperatorChar() || isPunctuationChar()) {
							return newToken(content);
						}
						state = State.TOKEN_END;
						break;
					}
					content += code[position];
					nextChar();
					break;
					
				case State.LITERAL:
					if (isQuote()) {
						state = State.TOKEN_END;
						plusQuotes();
					} else {
						content += code[position];
						nextChar();
					}
					break;
					
				case State.WORD:
					if (isWordChar() || isNumberChar() || isUnderlineChar()) {
						content += code[position];
						nextChar();
					}
					else if (isCarriageReturn() || isOperatorChar() || isPunctuationChar()) {
						if (isOperatorChar() || isPunctuationChar()) {
							return newToken(content);
						}
						state = State.TOKEN_END;
						break;
					}
					else {
						LexicalException.invalidSymbol(code[position], line);
					}
					break;
					
				case State.NUMBER:
					if (isNumberChar() || isPoint()) {
						if (isPoint()) {
							if (tokenType == TokenType.REAL) {
								LexicalException.realPoints(line);
							}
							tokenType = TokenType.REAL;
						}
						content += code[position];
						nextChar();
 					}
					else if (isCarriageReturn() || isPunctuationChar()) {
						if (isOperatorChar() || isPunctuationChar()) {
							return newToken(content);
						}
						state = State.TOKEN_END;
						break;
					}
					else {
						LexicalException.invalidSymbol(code[position], line);
					}
					break;
					
				case State.PUNCTUATION:
						content += code[position];
						state = State.TOKEN_END;
						break;
						
				case State.TOKEN_END:
					nextChar();
					if (!verifyToken(content)) {
						state = State.INITIAL;
						break;
					}
					return newToken(content);
					
				case State.PROGRAM_END:
					verifyToken(content);
					return newToken(content);
			}
		}
	}
	
	/**
	 * Fun��o que realiza as valida��es finais de um token quanto ao seu tipo, e o retorna se tudo correto
	 * @param content conte�do a ser inserido no token
	 * @return
	 */
	public Token newToken(String content) {
		verifyToken(content);
		return new Token(content, tokenType);
	}
	
	/**
	 * Define o estado da an�lise, de forma que este analisador l�xico tem a ideia de ser um aut�mato (m�quina de estado)
	 */
	public void takeState() {
		if (isQuote()) {
			state     = State.LITERAL;
			tokenType = TokenType.LITERAL;
			plusQuotes();
			nextChar();
		} 
		else if (isOperatorChar()) {
			state 	  = State.OPERATOR;
			tokenType = TokenType.OPERATOR;
		}
		else if (isPunctuationChar()) {
			state 	  = State.PUNCTUATION;
			tokenType = TokenType.PUNCTUATION;
		}
		else if (isWordChar()) {
			state     = State.WORD;
			tokenType = TokenType.VAR;
		} 
		else if (isNumberChar()) {
			state 	  = State.NUMBER;
			tokenType = TokenType.INTEGER;
		}
		else if (isLineBreak()) {
			line++;
			nextChar();
		}
		else if (isTabChar()) {
			nextChar();
		}
		else {
			LexicalException.invalidSymbol(code[position], line);
		}
	}
	
	/**
	 * Realiza as verifica��es do conte�do a ser inserido no token, assim definido o seu tipo
	 * @param content conte�do a ser inserido no token
	 * @return
	 */
	public boolean verifyToken(String content) {
		switch (tokenType) {
		case TokenType.LITERAL:
			verifyLiteral(content);
			break;
		case TokenType.VAR:
			verifyWord(content);
			break;
		case TokenType.INTEGER:
			verifyNumber(content);
			break;
		case TokenType.REAL:
			verifyNumber(content);
			break;
		case TokenType.OPERATOR:
			verifyOperator(content);
			break;
		}
		if (isNullContent(content)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Verifica se a literal est� corretamente montado
	 * @param content
	 */
	public void verifyLiteral(String content) {
		if (sQuotes % 2 != 0 || dQuotes % 2 != 0) {
			LexicalException.invalidLiteral(content, line);
		}
	}
	
	/**
	 * Verifica se a palavra est� corretamente montada
	 * @param content
	 */
	public void verifyWord(String content) {
		if (content.endsWith("_")) {
			LexicalException.underlineFinish(line);
		}
		else if (isProgramBeginning(content)) {
			tokenType = TokenType.PROGRAM;
		}
		else if (isBegin(content)) {
			tokenType = TokenType.BEGIN;
		}
		else if (isEnd(content)) {
			tokenType = TokenType.END;
		}
		else if (isKeyWord(content) && !isProgramBeginning(content) && !isBegin(content) && !isEnd(content)) {
			tokenType = TokenType.KEYWORD;
		} else {
			tokenType = TokenType.VAR;
		}
	}
	
	/**
	 * Verifica se o n�mero est� corretamente montado
	 * @param content
	 */
	public void verifyNumber(String content) {
		if (!content.matches("[0-9]{1,}.[0-9]{1,}") && !content.matches("[0-9]{1,}")) {
			LexicalException.invalidNumber(content, line);
		}
	}
	
	/**
	 * Verifica o tipo do perador e se ele est� corretamente montado
	 * @param content
	 */
	public void verifyOperator(String content) {
		if (mathOperators.contains(content)) {
			tokenType = TokenType.MAT_OPERATOR;
		}
		else if (assOperators.contains(content)) {
			tokenType = TokenType.ASS_OPERATOR;
		}
		else if (logOperators.contains(content)) {
			tokenType = TokenType.LOG_OPERATOR;
		}
		else if (punOperators.contains(content)) {
			tokenType = TokenType.PUNCTUATION;
		}
		else {
			LexicalException.invalidOperator(content, line);
		}
	}
	
	/**
	 * Realiza a contagem das aspas para a montagem de literais
	 */
	public void plusQuotes() {
		if (code[position] == '\'') {
			sQuotes++;
		}
		else {
			dQuotes++;
		}
	}
	
	/**
	 * Verifica se o conte�do � nulo
	 * @param content
	 * @return
	 */
	public boolean isNullContent(String content) {
		return content == " " || content == "";
	}
	
	/**
	 * Verifica se o caracter em quest�o � uma letra do alfabeto
	 * @return
	 */
	public boolean isWordChar() {
		return (code[position] >= 'a' && code[position] <= 'z') || (code[position] >= 'A' && code[position] <= 'Z');
	}
	
	/**
	 * Verifica se o caracter em quest�o � um underline
	 * @return
	 */
	public boolean isUnderlineChar() {
		return code[position] == '_';
	}
	
	/**
	 * Verifica se o caracter em quest�o � um n�mero
	 * @return
	 */
	public boolean isNumberChar() {
		return code[position] >= '0' && code[position] <= '9';
	}
	
	/**
	 * Verifica se o caracter em quest�o � um ponto
	 * @return
	 */
	public boolean isPoint() {
		return code[position] == '.';
	}
	
	/**
	 * Verifica se o caracter em quest�o � uma aspa simples ou dupla
	 * @return
	 */
	public boolean isQuote() {
		return code[position] == '\'' || code[position] == '\"';
	}
	
	/**
	 * Verifica se o caracter em quest�o � um caracter de tabula��o
	 * @return
	 */
	public boolean isTabChar() {
		return isCarriageReturn() || isTab();
	}
	
	/**
	 * Verifica se o caracter em quest�o � uma volta para o in�cio de uma linha
	 * @return
	 */
	public boolean isCarriageReturn() {
		return code[position] == '\r';
	}
	
	/**
	 * Verifica se o caracter em quest�o � um 'tab'
	 * @return
	 */
	public boolean isTab() {
		return code[position] == '\t';
	}
	
	/**
	 * Verifica se o caracter em quest�o � uma quebra de linha
	 * @return
	 */
	public boolean isLineBreak() {
		return code[position] == '\n';
	}
	
	/**
	 * Verifica se um token chegou ao fim, por um espa�o
	 * @return
	 */
	public boolean isTokenEnd() {
		return code[position] == ' ';
	}
	
	/**
	 * Verifica se o caractere em quest�o � um tipo de operador
	 * @return
	 */
	public boolean isOperatorChar() {
		return code[position] == '<' || code[position] == '>' || code[position] == '=' || code[position] == '!' ||
			   code[position] == '+' || code[position] == '-' || code[position] == '*' || code[position] == '/';
	}
	
	/**
	 * Verifica se o caractere em quest�o � um caractere de pontua��o final, o ponto e v�rgula
	 * @return
	 */
	public boolean isPunctuationChar() {
		return code[position] == ';';
	}
	
	/**
	 * Verifica se a an�lise chegou ao fim do programa, ou seja, n�o restam  mais caracteres para serem analisados
	 * @return
	 */
	public boolean isProgramEnd() {
		return position == code.length;
	}
	
	/**
	 * Verifica se o conte�do do token a ser inserido � uma palavra chave
	 * @return
	 */
	public boolean isKeyWord(String tokenContent) {
		return keyWords.contains(tokenContent);
	}
	
	/**
	 * Verifica se o conte�do do token a ser inserido � o in�cio do programa ('program')
	 * @return
	 */
	public boolean isProgramBeginning(String content) {
		return content.equals(keyWords.get(TokenType.PROGRAM));
	}
	
	/**
	 * Verifica se o conte�do do token a ser inserido � um in�cio de bloco de instru��es ('begin')
	 * @return
	 */
	public boolean isBegin(String content) {
		return content.equals(keyWords.get(TokenType.BEGIN));
	}
	
	/**
	 * Verifica se o conte�do do token a ser inserido � um fim de bloco de instru��es ('end')
	 * @return
	 */
	public boolean isEnd(String content) {
		return content.equals(keyWords.get(TokenType.END));
	}
	
	/**
	 * Retorna o atributo que indica se o programa acabou e n�o h� mais tokens para serem analisados
	 * @return
	 */
	public boolean isFinish() {
		return this.finish;
	}

	/**
	 * Avan�a a an�lise para o pr�ximo caractere
	 */
	public void nextChar() {
		position++;
	}
	
	/**
	 * Retorna a linha atual em que a an�lise se encontra
	 * @return
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * Define os caracteres do programa em quest�o a ser analisado
	 */
	public void setCode(char[] code) {
		this.code = code;
	}

	/**
	 * Define as palavras reservadas do programa
	 */
	public void setKeyWords() {
		this.keyWords.add("program");
		this.keyWords.add("begin");
		this.keyWords.add("end");
		this.keyWords.add("var");
		this.keyWords.add("while");
		this.keyWords.add("do");
		this.keyWords.add("for");
		this.keyWords.add("to");
		this.keyWords.add("if");
		this.keyWords.add("then");
		this.keyWords.add("else");
		this.keyWords.add("repeat");
		this.keyWords.add("until");
	}
	
	/**
	 * Define os operadores matem�ticos do programa
	 */
	public void setMathOperators() {
		this.mathOperators.add("+");
		this.mathOperators.add("-");
		this.mathOperators.add("*");
		this.mathOperators.add("/");
		this.mathOperators.add("%");
	}
	
	public List<String> getMathOperators() {
		return this.mathOperators;
	}
	
	/**
	 * Define os operadores de atribui��o do programa
	 */
	public void setAssOperators() {
		this.assOperators.add("=");
		this.assOperators.add("+=");
		this.assOperators.add("-=");
		this.assOperators.add("*=");
		this.assOperators.add("/=");
		this.assOperators.add("%=");
		this.assOperators.add("++");
		this.assOperators.add("--");
	}
	
	public List<String> getAssOperators() {
		return this.assOperators;
	}
	
	/**
	 * Define os operadores l�gicos do programa
	 */
	public void setLogOperators() {
		this.logOperators.add("==");
		this.logOperators.add("!=");
		this.logOperators.add(">=");
		this.logOperators.add("<=");
		this.logOperators.add(">");
		this.logOperators.add("<");
	}

	public List<String> getLogOperators() {
		return this.logOperators;
	}

	/**
	 * Define os operadores de pontua��o do programa
	 */
	public void setPunOperators() {
		this.punOperators.add(";");
	}

	public List<String> getPunOperators() {
		return this.punOperators;
	}
	
	
}
