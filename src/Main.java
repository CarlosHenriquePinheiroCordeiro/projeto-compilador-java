import java.util.List;

public class Main {

	public static void main(String[] args) {
		Parser parser = new Parser(new Lexical(Read.readFile("arquivo.txt")));
		parser.parse();
		
		do {
			
		} while (1 + 1 == 2);
	}

}
