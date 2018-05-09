import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * a class that creates a HuffmanTree from either a Huffman compressed .grin file,
 * or from a Map of the frequencies of characters in a file. Using that HuffmanTree,
 * encoding and decoding can be performed on files.
 * 
 * @author niehusst
 * @author builinh
 */
public class HuffmanTree {
	
	private PriorityQueue<Node> huffQueue;
	private Map<Short, String> code;
	public Node root;
	
	/**
	 * creates a HuffmanTree from an input Map object that contains data, and the frequency of that data
	 * from the target that is to be compressed.
	 * 
	 * @param map - a Map from data to its frequency
	 */
	public HuffmanTree(Map<Short, Integer> map) {
		huffQueue = new PriorityQueue<>();
		
		//turn map contents into Nodes and add to priority queue
		for(Map.Entry<Short, Integer> entry : map.entrySet()) {
			Node leaf = new Node(entry.getKey(), entry.getValue());
			huffQueue.add(leaf);
		}
		//add eof to queue
		Short eof = 256;
		huffQueue.add(new Node(eof, 1));
		//turn queue into Huffman tree
		while(huffQueue.size() >= 2) {
			Node inner = new Node(huffQueue.poll(), huffQueue.poll());
			huffQueue.add(inner);
		}
		this.root = huffQueue.poll();
	}
	
	/**
	 * construct a HuffmanTree from an input stream of a Huffman compressed file
	 * 
	 * @param in - stream from compressed file
	 */
	public HuffmanTree(BitInputStream in) {
		huffQueue = new PriorityQueue<>();
		huffQueue.add(HuffmanTreeH(in));	
		this.root = huffQueue.poll();
	}
	
	 /**
	  * helper to the input stream HuffmanTree constructor
	  * 
	  * @param in - stream from compressed file
	  * @return - a Node that is to be inserted into the HuffmanTree
	  */
	private Node HuffmanTreeH(BitInputStream in) {
		int ret;
		
		while ((ret = in.readBit()) != -1) {
			if (ret == 0) {
				//might not work correctly if just casting int to short
				short data = (short) in.readBits(9);
				//frequency is -1 because it doesn't matter when not building tree from Map
				return new Node(data, -1);
			} else {
				Node left = HuffmanTreeH(in);
				Node right = HuffmanTreeH(in);
				Node newNode = new Node(left, right);
				return newNode;
			}
		}
		//if the stream starts as empty, return null
		return null;
	}
	
	/**
	 * helper for the serialize function. Recursively writes the data
	 * about the HuffmanTree to out
	 * 
	 * @param out - the stream to write to
	 * @param cur - the current node in the tree
	 */
	private void serializeH(BitOutputStream out, Node cur) {
		if (cur.isLeaf()) {
			out.writeBit(0);
			out.writeBits(cur.getData(), 9);			
		} else {
			out.writeBit(1);
			serializeH(out, cur.getLeft());
			serializeH(out, cur.getRight());			
		}
	}
	
	/**
	 * serialize the HuffmanTree, writing its structure to out
	 * 
	 * @param out - target stream 
	 */
	public void serialize(BitOutputStream out) {
		Node cur = root;
		serializeH(out, cur);
	}
	
	/**
	 * encode all data from in, and write compressed data to out
	 * 
	 * @param in - uncompressed source stream
	 * @param out - target compressed stream
	 */
	public void encode(BitInputStream in, BitOutputStream out) {
		//first write the tree we are using to target
		this.serialize(out);
		//get the code for the data in Huffman tree and put in Hashmap
		Node cur = root;
		this.code = new HashMap<>();
		String path = "";
		findAllCodes(cur, path, code);
		
		//encode the data from in
		short data;
		while((data = (short) in.readBits(8)) != -1) {
			for(int i = 0; i < code.get(data).length(); i++) {
				//write the path for the next character from 'in' bit by bit
				if (code.get(data).charAt(i) == '0') {
					out.writeBit(0);
				} else {
					out.writeBit(1);
				}
			}
		}
	}
	
	/**
	 * find all path codes for this HuffmanTree and put into a code.
	 * 
	 * @param cur - current node in HuffmanTree
	 * @param path - the list of bits that represents the path (and code) to a certain leaf
	 * @param code - the mapping of all values in HuffmanTree to their Huffman codes
	 */
	private void findAllCodes(Node cur, String path, Map<Short, String> code) {
		if(cur.isLeaf()) {
			//add path data pair to code map. Then clear path for next leaf??
			code.put(cur.getData(), path);             
		} else {
			//record the path taken into path list and recurse
			findAllCodes(cur.getLeft(), path + "0", code);
			findAllCodes(cur.getRight(), path + "1", code);			
		}
	}
	
	/**
	 * read compressed data from a stream, and using HuffmanTree, decompress
	 * it and write data to target stream out
	 * 
	 * @param in - a Huffman encoded stream
	 * @param out - the stream to write decoded values to
	 */
	public void decode(BitInputStream in, BitOutputStream out) {
		int bit;
		Node cur = root;
		
		while((bit = in.readBit()) != -1) {
			decodeH(in, out, cur.getDirection(bit));
		}
	}
	
	/**
	 * helper for decode. Handles traversal of HuffmanTree to correct LeafNode
	 * and writing the data at that leaf
	 *  
	 * @param in - source stream of compressed data
	 * @param out - target stream to write decompressed data to
	 */
	private void decodeH(BitInputStream in, BitOutputStream out, Node cur) {
		if (cur.isLeaf()) {	
			if (cur.getData() != 256) {
				out.writeBits(cur.getData(), 8);
			}
			return;
		} else {
			int bit = in.readBit();
			decodeH(in, out, cur.getDirection(bit));
		}
	}
	


}
