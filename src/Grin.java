import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * a driver class for the HuffmanTree class.
 * 
 * @author niehusst
 * @author builinh
 */
public class Grin {


	public static void printTree(HuffmanTree tree, Node cur) {		
		if (cur.isLeaf()) {
			System.out.println("tree is" + cur.getData());
		} else {
			printTree(tree, cur.getLeft());
			printTree(tree, cur.getRight());
		}
	}
	
	
	/**
	 * a function for decoding a .grin file
	 * 
	 * @param infile - a Huffman compressed .grin file
	 * @param outfile - target write file for decompressed data
	 * @throws IOException - thrown when a file can't be opened
	 * @throws IllegalArgumentException - thrown when infile is not a .grin file
	 */
	public static void decode(String infile, String outfile) throws IOException, IllegalArgumentException {
		BitOutputStream out = new BitOutputStream(outfile);
		BitInputStream in = new BitInputStream(infile);
		if(in.readBits(32) != 1846) {
			//not a grin file!
			throw new IllegalArgumentException();
		} else {
			//decode grin file
			HuffmanTree tree = new HuffmanTree(in);	
			tree.decode(in, out);
		}
		in.close();
		out.close();
	}
	
	/**
	 * encodes a file to .grin compressed format
	 * 
	 * @param infile - file to have its data compressed
	 * @param outfile - target file to write compressed data to
	 * @throws IOException - thrown when there is an error opening a file stream
	 */
	public static void encode(String infile, String outfile) throws IOException {
		BitOutputStream out = new BitOutputStream(outfile);
		BitInputStream in = new BitInputStream(infile);
		Map<Short, Integer> frequencyMap = createFrequencyMap(infile);

		HuffmanTree tree = new HuffmanTree(frequencyMap);
		//add magic grin number as header
		out.writeBits(1846, 32);
		tree.encode(in, out);
		in.close();
		out.close();
	}
	
	/**
	 * creates a Map from a character (that is present in file) to the
	 * frequency of which appears in file. Used for initializing a HuffmanTree
	 * 
	 * @param file - file that the frequency map is created from
	 * @return - Map, the frequency map of all characters in the file
	 * @throws IOException - thrown when there is an error opening a file stream
	 */
	public static Map<Short, Integer> createFrequencyMap(String file) throws IOException {
		BitInputStream in = new BitInputStream(file);
		Map<Short, Integer> frequencyMap = new HashMap<>();
		while(in.hasBits()) {
			short bit = (short) in.readBits(8);
			if (frequencyMap.containsKey(bit)) {
				frequencyMap.put(bit, frequencyMap.get(bit) + 1);
			} else
				frequencyMap.put(bit, 1);
		}
		return frequencyMap;
	}
	
	/**
	 * the main function; reads command line input and performs user chosen commands.
	 * Also handles errors, should any exceptions be thrown.
	 * 
	 * @param args - command line input
	 */
	public static void main(String[] args) {
		String task = args[0];
		String infile = args[1];
		String outfile = args[2];
		
		if(task.toLowerCase().equals("encode")) {
			try {
				encode(infile, outfile);
			} catch(IOException e) {
				System.err.println("ERROR: IO error, stream failed to open specified encode files");
			}
		} else if(task.toLowerCase().equals("decode")) {
			try {
				decode(infile, outfile);
			} catch(IOException e) {
				System.err.println("ERROR: IO error, stream failed to open specified decode files");
			}
		} else {
			System.err.println("ERROR: Given task does not match correct usage. (java Grin <encode|decode> <infile> <outfile>)");
		}
		
	}
}
