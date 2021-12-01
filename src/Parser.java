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
	
	public boolean parse() {
		program();
		return true;
	}

	public void program() {
		Token token = nextToken();
		if (!isProgram(token)) {
			SyntaxException.invalidProgram(token, getLex().getLine());
		}
		//begin();
	}
	
	public void begin() {
		Token token = nextToken();
		if (!isBegin(token)) {
			SyntaxException.invalidStart(token, getLex().getLine());
		}
		//statementList();
		//end();
	}
	
	public void statementList() {
		Token token = nextToken();
		if (isBegin(token)) {
			
		}
		
		
	}
	
	public void end() {
		Token token = nextToken();
		if (!isEnd(token)) {
			SyntaxException.invalidEnd(token, getLex().getLine());
		}
	}
	
	public boolean isProgram(Token token) {
		return token.getType() == TokenType.PROGRAM;
	}
	
	public boolean isBegin(Token token) {
		return token.getType() == TokenType.BEGIN;
	}
	
	public boolean isEnd(Token token) {
		return token.getType() == TokenType.END;
	}
	
	public Token nextToken() {
		return getLex().newToken();
	}
	
	public Lexical getLex() {
		return lex;
	}

	public void setLex(Lexical lex) {
		this.lex = lex;
	}
	
	
}
