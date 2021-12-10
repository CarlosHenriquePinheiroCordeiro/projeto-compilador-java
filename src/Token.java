/**
 * Classe que define o Token do compilador
 * @author Carlos Henrique Pinheiro Cordeiro
 *
 */
public class Token {

	/**
	 * Conteúdo/Nome do token
	 */
	private String content;
	
	/**
	 * Tipo do token
	 */
	private byte type;
	
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