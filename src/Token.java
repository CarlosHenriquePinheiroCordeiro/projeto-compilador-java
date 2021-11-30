/**
 * Token do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Token {

	private String content;
	private int    type;
	
	public Token(String content, int type) {
		setContent(content);
		setType(type);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "\nToken [content: '" + content + "'\n\t  type: " + TokenType.type[type] + "]";
	}
	
	
}
