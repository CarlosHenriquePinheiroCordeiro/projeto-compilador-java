/**
 * Analisador Sintático (Parser) do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Parser {
	
	private Lexical lex;
	
	public Parser(Lexical lex) {
		setLex(lex);
		program();
	}
	
	public boolean parse() {
		
		return true;
	}

	public void program() {
		Token token = lex.newToken();
		if (token.getType() != TokenType.PROGRAM) {
			SyntaxException.invalidProgram(token, lex.getLine());
		}
		begin();
	}
	
	public void begin() {
		Token token = lex.newToken();
		if (token.getType() != TokenType.BEGIN) {
			SyntaxException.invalidStart(token, lex.getLine());
		}
	}
	
	public void end() {
		
	}
	
	public Lexical getLex() {
		return lex;
	}

	public void setLex(Lexical lex) {
		this.lex = lex;
	}
	
	
}
