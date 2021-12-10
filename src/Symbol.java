import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por representar cada símbolo identificado pelo compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Symbol {

	/**
	 * Nome da variável
	 */
	private String name;
	
	/**
	 * Tipo da variável
	 */
	private byte type;
	
	/**
	 * Quantidade de bits alocados na memória
	 */
	private int bits;
	
	/**
	 * Valor da variável
	 */
	private String value;
	
	/**
	 * Linhas que a variável é utilizada no programa
	 */
	private List<Integer> lines = new ArrayList<Integer>();
	
	/**
	 * Linha onde o símbolo foi declarado
	 */
	private int declarationLine = 0;
	
	/**
	 * Simboliza se a variável foi utilizada ou não
	 */
	private boolean used;
	
	public Symbol(String name, byte type, int line) {
		setName(name);
		setType(type);
		setDeclarationLine(line);
		//setValue(name);
		//setBits();
	}
	
	public void addLine(int line) {
		getLines().add(line);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getType() {
		return type;
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public int getBits() {
		return bits;
	}
	
	public void setBits() {
		int bits = 0;
		this.bits = bits;
	}
	
	public List<Integer> getLines() {
		return lines;
	}
	
	public int getDeclarationLine() {
		return declarationLine;
	}

	public void setDeclarationLine(int declarationLine) {
		this.declarationLine = declarationLine;
	}

	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Symbol [nome=" + name + ", tipo=" + TokenType.type[type] + ", bits=" + bits + ", declarationLine="+getDeclarationLine()+", inLines=" + printLines() + ", used=" + used;
	}
	
	/**
	 * Retorna as linhas que o símbolo é utilizado
	 * @return
	 */
	public String printLines() {
		if (getLines().isEmpty()) {
			return "none";
		}
		String lines = "";
		for (int line : getLines()) {
			lines += " "+line;
		}
		return lines;
	}
	
	
}