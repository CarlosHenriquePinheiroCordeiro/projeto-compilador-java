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
	 * Express�o atual para atribui��o de valor para s�mbolo
	 */
	private List<Token> expression = new ArrayList<Token>();

	/**
	 * S�mbolo atual em evid�ncia para atribui��es
	 */
	private Symbol actualSymbol = null;
	
	/**
	 * Total de escopos do programa
	 */
	private int scopes = 0;

	/**
	 * Escopo atual do programa
	 */
	private int actualScope = 0;

	/**
	 * Traz um novo s�mbolo para a tabela de s�mbolos se este j� n�o existir
	 * @param token
	 */
	public void newSymbol(Token token, int line) {
		if (!symbolExists(token, line)) {
			Symbol symbol = new Symbol(token.getContent(), token.getType(), line);
			getSymbolTable().get(getActualScope() - 1).add(symbol);
			setActualSymbol(symbol);
		}
		else {
			getSymbol(token.getContent()).addLine(line);
			getSymbol(token.getContent()).setUsed(true);
			setActualSymbol(getSymbol(token.getContent()));
		}
	}

	/**
	 * Retorna um s�mbolo pelo nome, se existir
	 * @param name
	 * @return
	 */
	public Symbol getSymbol(String name) {
		for (List<Symbol> scope : getSymbolTable()) {
			for (Symbol symbol : scope) {
				if (symbol.getName().equals(name)) {	
					return symbol;
				}
			}
		}
		return null;
	}
	
	/**
	 * Aumenta o valor do escopo atual do programa, auxiliando nas defini��es de escopo das vari�veis
	 */
	public void plusActualScope() {
		this.actualScope++;
		verifyScope();
	}

	/**
	 * Diminui o valor do escopo atual do programa, auxiliando nas defini��es de escopo das vari�veis
	 */
	public void subtractActualScope() {
		this.actualScope--;
	}

	/**
	 * Verifica se o Token que viraria um s�mbolo j� n�o se encontra como um na tabela de s�mbolos
	 * @param token
	 * @return
	 */
	public boolean symbolExists(Token token, int line) {
		for (List<Symbol> scope : getSymbolTable()) {
			for (Symbol symbol : scope) {
				if (symbol.getName().equals(token.getContent())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verifica e atende ao total de escopos do programa
	 */
	public void verifyScope() {
		if (getActualScope() > getScopes()) {
			setScopes(getActualScope());
		}
	}
	
	/**
	 * Adiciona um item � express�o de atribui��o atual
	 * @param expressionItem
	 */
	public void addToExpression(Token expressionItem) {
		getExpression().add(expressionItem);
	}
	
	/**
	 * Encerra a express�o atual, atribuindo valor ao s�mbolo atual
	 */
	public void finishExpression() {
		getSymbol(getActualSymbol().getName()).setValue(buildValueFromExpression());
		getExpression().clear();
	}
	
	/**
	 * Retorna o valor da express�o atual, para atribu�-lo ao s�mbolo atual
	 * @param <T>
	 * @return
	 */
	public <T> T buildValueFromExpression() {
		if (isConcatenation()) {
			return buildValueConcatenation();
		}
		else {
			return buildValue();
		}
	}
	
	/**
	 * Constr�i a express�o de atribui��o de valor, para express�es com concatena��es
	 * @param <T>
	 * @return
	 */
	public <T> T buildValueConcatenation() {
		String expression= "";
		for (Token token : getExpression()) {
			
		}
		return null;
	}
	
	/**
	 * Constr�i a express�o de atribui��o de valor, para express�es num�ricas
	 * @param <T>
	 * @return
	 */
	public <T> T buildValue() {
		String expression= "";
		boolean isReal = false;
		for (Token token : getExpression()) {
			if (token.getType() == TokenType.REAL)
				isReal = true;
			
		}
		return null;
	}
	
	/**
	 * Retorna se a express�o atual cont�m concatena��o ou n�o
	 * @return
	 */
	public boolean isConcatenation() { 
		for (Token token : getExpression()) {
			if (token.getType() == TokenType.LITERAL) 
				return true;
		}	
		return false;
	}

	/**
	 * Retorna a tabela de s�mbolos
	 * @return
	 */
	public List<List> getSymbolTable() {
		return symbolTable;
	}

	/**
	 * Retorna a express�o de atribui��o
	 * @return
	 */
	public List<Token> getExpression() {
		return expression;
	}

	/**
	 * Retorna o s�mbolo atual em evid�ncia para express�es de atribui��o
	 * @return
	 */
	public Symbol getActualSymbol() {
		return actualSymbol;
	}

	/**
	 * Define o s�mbolo atual em evid�ncia para express�es de atribui��o
	 * @param actualSymbol
	 */
	public void setActualSymbol(Symbol actualSymbol) {
		this.actualSymbol = actualSymbol;
	}

	/**
	 * Retorna o total de escopos do programa
	 * @return
	 */
	public int getScopes() {
		return scopes;
	}

	/**
	 * Defini��o dos escopos totais da tabela de s�mbolos
	 * @param scopes
	 */
	public void setScopes(int scopes) {
		this.scopes = scopes;
		for (int x = 0; x < (getScopes() - getSymbolTable().size()); x++) {
			getSymbolTable().add(new ArrayList<Symbol>());
		}
	}

	/**
	 * Retorna o escopo atual do programa
	 * @return
	 */
	public int getActualScope() {
		return actualScope;
	}

	/**
	 * Define o escopo atual do programa
	 * @param actualScope
	 */
	public void setActualScope(int actualScope) {
		this.actualScope = actualScope;
	}

	@Override
	public String toString() {
		return "SymbolTable [ \n"+printSymbols()+" \n]";
	}

	/**
	 * Retorna a String dos s�mbolos da tabela para a exibi��o
	 * @return
	 */
	public String printSymbols() {
		String symbols = "";
		for (int scope = 0; scope < getSymbolTable().size(); scope++) {
			for (Symbol symbol : (List<Symbol>)getSymbolTable().get(scope)) {
				symbols += symbol.toString()+", scope="+(scope + 1)+"]\n";
			}
		}
		return symbols;
	}


}