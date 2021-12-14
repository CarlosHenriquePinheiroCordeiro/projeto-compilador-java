import java.util.List;
import java.util.Scanner;
import javax.script.*;

/**
 * Projeto de Compilador para trabalho de componente curricular Compiladores I
 * Instituto Federal Catarinenes (IFC) de Rio do Sul - SC
 * Bacharelado em Ciências da Computação
 * 
 * Restrições: JDK suportada até versão 14
 * 
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Main {

	public static void main(String[] args) throws ScriptException {		
		/*System.out.println("Digite:");
		System.out.println("1 - Análise Léxica e exibição dos Tokens.");
		System.out.println("2 - Análise Sintática + Exibição dos Símbolos.");
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
			//System.out.println("Análise sintática realizada com sucesso!");
		//}
	}

}
