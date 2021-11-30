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
	
	public boolean Parse() {
		
		return true;
	}

	public void teste() {
		for (int x = 0; x < 4; x ++) {
			System.out.println(getLex().newToken());
		}
	}
	
	public Lexical getLex() {
		return lex;
	}

	public void setLex(Lexical lex) {
		this.lex = lex;
	}
	
	
}
