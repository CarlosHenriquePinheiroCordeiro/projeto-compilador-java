/**
 * Token do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Token {

	private String content;
	private byte    type;
	
	public Token(String content, byte type) {
		setContent(content);
		setType(type);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "\nToken [content: '" + content + "'\n\t  type: " + TokenType.type[type] + "]";
	}
	
	
}
