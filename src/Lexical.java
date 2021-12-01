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
	
	private int  position  		   	   = 0;
	private int  line 	 	 	       = 1;
	private int  sQuotes 		       = 0;
	private int  dQuotes 			   = 0;
	private int  state 	  		       = State.INITIAL;
	private byte tokenType 		       = TokenType.UNDEFINED;
	
	public Lexical(char[] code) {
		setCode(code);
		setKeyWords();
		setMathOperators();
		setAssOperators();
		setLogOperators();
	}

	public List<Token> analisys() {
		do {
			tokens.add(newToken());
		} while (!isProgramEnd()); //Equanto ainda houver caracteres para serem lidos
		return tokens;
	}
	
	public Token newToken() {
		String content = "";
		state 		   = State.INITIAL;
		tokenType 	   = TokenType.UNDEFINED;
		while (true) {
			if (isProgramEnd()) {
				state = State.PROGRAM_END;
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
	
	public Token newToken(String content) {
		verifyToken(content);
		return new Token(content, tokenType);
	}
	 
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
	
	public void verifyLiteral(String content) {
		if (sQuotes % 2 != 0 || dQuotes % 2 != 0) {
			LexicalException.invalidLiteral(content, line);
		}
	}
	
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
	
	public void verifyNumber(String content) {
		if (!content.matches("[0-9]{1,}.[0-9]{1,}") && !content.matches("[0-9]{1,}")) {
			LexicalException.invalidNumber(content, line);
		}
	}
	
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
	
	public void plusQuotes() {
		if (code[position] == '\'') {
			sQuotes++;
		}
		else {
			dQuotes++;
		}
	}
	
	public boolean isNullContent(String content) {
		return content == " " || content == "";
	}
	
	public boolean isWordChar() {
		return (code[position] >= 'a' && code[position] <= 'z') || (code[position] >= 'A' && code[position] <= 'Z');
	}
	
	public boolean isUnderlineChar() {
		return code[position] == '_';
	}
	
	public boolean isNumberChar() {
		return code[position] >= '0' && code[position] <= '9';
	}
	
	public boolean isPoint() {
		return code[position] == '.';
	}
	
	public boolean isQuote() {
		return code[position] == '\'' || code[position] == '\"';
	}
	
	public boolean isTabChar() {
		return isCarriageReturn() || isTab();
	}
	
	public boolean isCarriageReturn() {
		return code[position] == '\r';
	}
	
	public boolean isTab() {
		return code[position] == '\t';
	}
	
	public boolean isLineBreak() {
		return code[position] == '\n';
	}
	
	public boolean isTokenEnd() {
		return code[position] == ' ';
	}
	
	public boolean isProgramEnd() {
		return position == code.length;
	}
	
	public boolean isKeyWord(String tokenContent) {
		return keyWords.contains(tokenContent);
	}
	
	public boolean isOperatorChar() {
		return code[position] == '<' || code[position] == '>' || code[position] == '=' || code[position] == '!' ||
			   code[position] == '+' || code[position] == '-' || code[position] == '*' || code[position] == '/';
	}
	
	public boolean isPunctuationChar() {
		return code[position] == ';';
	}
	
	public boolean isProgramBeginning(String content) {
		return content.equals(keyWords.get(TokenType.PROGRAM));
	}
	
	public boolean isBegin(String content) {
		return content.equals(keyWords.get(TokenType.BEGIN));
	}
	
	public boolean isEnd(String content) {
		return content.equals(keyWords.get(TokenType.END));
	}

	public void nextChar() {
		position++;
	}
	
	public int getLine() {
		return this.line;
	}

	public void setCode(char[] code) {
		this.code = code;
	}

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
	
	public void setMathOperators() {
		this.mathOperators.add("+");
		this.mathOperators.add("-");
		this.mathOperators.add("*");
		this.mathOperators.add("/");
		this.mathOperators.add("%");
	}
	
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
	
	public void setLogOperators() {
		this.logOperators.add("==");
		this.logOperators.add("!=");
		this.logOperators.add(">=");
		this.logOperators.add("<=");
		this.logOperators.add(">");
		this.logOperators.add("<");
	}

	public void setPunOperators() {
		this.punOperators.add(";");
	}
	
	
}
