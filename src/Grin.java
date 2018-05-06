import java.util.HashMap;
import java.util.Map;

public class Grin {

	public static void decode(String infile, String outfile) {
		
	}
	
	public static void encode(String infile, String outfile) {
		
	}
	
	public static Map<Short, Integer> createFrequencyMap(String file) {
		return null;
	}
	
	public static void main(String[] args) {
		Map<Short, Integer> info = new HashMap<>();
		info.put((short) 200, 4);
		info.put((short) 100, 6);
		info.put((short) 50, 10);
		
		HuffmanTree tree = new HuffmanTree(info);
		
		
	}
}
