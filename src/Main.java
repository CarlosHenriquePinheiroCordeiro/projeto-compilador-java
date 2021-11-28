import java.util.List;

public class Main {

	public static void main(String[] args) {
		Lexical lexical    = new Lexical(Read.readFile("arquivo.txt"));
		List<Token> tokens = lexical.analisys();
		for (Token token : tokens) {
			System.out.println(token);
		}
	}

}
