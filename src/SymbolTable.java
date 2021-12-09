import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
	
	/**
	 * Tabela de símbolos, funcionando como um conjunto de Lists que comportarão os símbolos.
	 * Cada um destes Lists representa um escopo, ou seja, variáveis que estão no escopo 1 
	 * se encontram na primeira lista desta variável. Já os símbolos de escopo 2 estão na segunda lista,
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
	 * Aumenta o valor do escopo atual do programa, auxiliando nas definições de escopo das variáveis
	 */
	public void plusActualScope() {
		this.actualScope++;
	}
	
	/**
	 * Diminui o valor do escopo atual do programa, auxiliando nas definições de escopo das variáveis
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
