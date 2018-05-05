import java.util.Map;
import java.util.PriorityQueue;
import java.io.*;
import java.lang.*;

public class HuffmanTree {
	
	private PriorityQueue<Node> huffQueue;
	
	/**
	 * creates a HuffmanTree from an input Map object that contains data, and the frequency of that data
	 * from the target that is to be compressed.
	 * @param map - a Map from data to its frequency
	 */
	public HuffmanTree(Map<Short, Integer> map) {
		//turn map contents into LeafNodes and add to priority queue
		for(Map.Entry<Short, Integer> entry : map.entrySet()) {
			Node leaf = new LeafNode(entry.getKey(), entry.getValue());
			huffQueue.add(leaf);
		}
		
		//turn queue into Huffman tree
		while(huffQueue.size() >= 2) {
			InteriorNode inner = new InteriorNode(huffQueue.poll(), huffQueue.poll());
			huffQueue.add(inner);
		}
	}
	
	/**
	 * construct a HuffmanTree from an input stream of a Huffman compressed file
	 * @param in - stream from compressed file
	 */
	public HuffmanTree(BitInputStream in) {
		
	}

}
