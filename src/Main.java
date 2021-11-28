import java.util.List;

public class Main {

	public static void main(String[] args) {
		Lexical lex    = new Lexical(Read.readFile("arquivo.txt"));
		List<Token> tokens = lex.analisys();
		for (Token token : tokens) {
			System.out.println(token);
		}
	}

}
