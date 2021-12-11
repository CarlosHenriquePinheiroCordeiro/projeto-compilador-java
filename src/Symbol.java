import java.util.ArrayList;
import java.util.List;

/**
 * Classe respons�vel por representar cada s�mbolo identificado pelo compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 * @param <T>
 *
 */
public class Symbol<T> {

	/**
	 * Nome da vari�vel
	 */
	private String name;
	
	/**
	 * Tipo da vari�vel
	 */
	private byte type = TokenType.UNDEFINED;
	
	/**
	 * Quantidade de bits alocados na mem�ria
	 */
	private int bits;
	
	/**
	 * Valor da vari�vel
	 */
	private T value;
	
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
	
	public Symbol(String name, int line) {
		setName(name);
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Symbol [name=" + getName()    + ", type=" + TokenType.type[getType()] 
				+ ", value="   +getValue()    + ", bits=" + getBits() + ", declarationLine="+getDeclarationLine()
				+", inLines="  + printLines() + ", used=" + isUsed();
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