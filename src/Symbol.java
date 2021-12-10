import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por representar cada símbolo identificado pelo compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Symbol {

	private String    	  name;
	private byte      	  type;
	private int           bits;
	private List<Integer> lines = new ArrayList<Integer>();
	private boolean  	  used;
	
	public Symbol(String name, byte type) {
		setName(name);
		setType(type);
		setBits();
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
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public String toString() {
		return "Symbol [nome=" + name + ", tipo=" + type + ", bits=" + bits + ", lines=" + lines + ", used=" + used
				+ "]";
	}
	
	public String printLines() {
		String lines = "";
		for (int line : getLines()) {
			lines += " "+line;
		}
		return lines;
	}
	
	
}
