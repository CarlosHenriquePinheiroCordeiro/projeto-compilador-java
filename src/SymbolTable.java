import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	
	/**
	 * Tabela de s�mbolos, funcionando como um conjunto de Lists que comportar�o os s�mbolos.
	 * Cada um destes Lists representa um escopo, ou seja, vari�veis que est�o no escopo 1 
	 * se encontram na primeira lista desta vari�vel. J� os s�mbolos de escopo 2 est�o na segunda lista,
	 * e assim por diante
	 */
	private List<List> symbolTable = new ArrayList<List>();
	
	/**
	 * Total de escopos do programa
	 */
	private int scopes;
	
	/**
	 * Escopo atual do programa
	 */
	private int actualScope;
	
	/**
	 * Aumenta o valor do escopo atual do programa, auxiliando nas defini��es de escopo das vari�veis
	 */
	public void plusActualScope() {
		this.actualScope++;
	}
	
	/**
	 * Diminui o valor do escopo atual do programa, auxiliando nas defini��es de escopo das vari�veis
	 */
	public void subtractActualScope() {
		this.actualScope--;
	}
	
	/**
	 * Verifica se o programa aumenta o total de escopos
	 */
	public void verifyScope() {
		if (getActualScope() > getScopes()) {
			setScopes(getActualScope());
		}
	}
	
	public List<List> getSymbolTable() {
		return symbolTable;
	}
	
	public int getScopes() {
		return scopes;
	}
	
	public void setScopes(int scopes) {
		this.scopes = scopes;
	}
	
	public int getActualScope() {
		return actualScope;
	}
	
	public void setActualScope(int actualScope) {
		this.actualScope = actualScope;
	}
	
	
}
