import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		/*System.out.println("Digite:");
		System.out.println("1 - An�lise L�xica e exibi��o dos Tokens.");
		System.out.println("2 - An�lise Sint�tica + An�lise L�xoca sem exibi��o dos tokens.");
		Scanner digite = new Scanner(System.in);
		byte op = digite.nextByte();
		if (op == 1) {
			Lexical l = new Lexical(Read.readFile("arquivo.txt"));
			List<Token> tks = l.analisys();
			for (Token tk : tks) {
				System.out.println(tk);
			}
		}
		else {*/
			Parser parser = new Parser(new Lexical(Read.readFile("arquivo.txt")));
		}
	//}

}
