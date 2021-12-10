import java.util.ArrayList;
import java.util.List;

/**
 * Classe respons�vel por representar cada s�mbolo identificado pelo compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Symbol {

	/**
	 * Nome da vari�vel
	 */
	private String name;
	
	/**
	 * Tipo da vari�vel
	 */
	private byte type;
	
	/**
	 * Quantidade de bits alocados na mem�ria
	 */
	private int bits;
	
	/**
	 * Valor da vari�vel
	 */
	private String value;
	
	/**
	 * Linhas que a vari�vel � utilizada no programa
	 */
	private List<Integer> lines = new ArrayList<Integer>();
	
	/**
	 * Linha onde o s�mbolo foi declarado
	 */
	private int declarationLine = 0;
	
	/**
	 * Simboliza se a vari�vel foi utilizada ou n�o
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
	 * Retorna as linhas que o s�mbolo � utilizado
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