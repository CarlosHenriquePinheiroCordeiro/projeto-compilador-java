import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Read {

	public static char[] readFile(String fileName) {
		try {
			String reading = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8).trim();
			return reading.toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
