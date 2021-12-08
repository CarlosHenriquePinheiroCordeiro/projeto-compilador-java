/**
 * Classe responsável por representar cada símbolo identificado pelo compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Symbol {

	private String  content;
	private String  name;
	private byte    type;
	private int     scope;
	private int     bits;
	private int     lines;
	private boolean used;
	
	public Symbol() {
		
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
	
	public int getScope() {
		return scope;
	}
	
	public void setScope(int scope) {
		this.scope = scope;
	}
	
	public int getBits() {
		return bits;
	}
	
	public void setBits(int bits) {
		this.bits = bits;
	}
	
	public int getLines() {
		return lines;
	}
	
	public void setLines(int lines) {
		this.lines = lines;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
}
