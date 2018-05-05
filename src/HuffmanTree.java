import java.util.ArrayList;
import java.util.HashMap;
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
	}
	
	/**
	 * construct a HuffmanTree from an input stream of a Huffman compressed file
	 * @param in - stream from compressed file
	 */
	public HuffmanTree(BitInputStream in) {
		huffQueue.add(HuffmanTreeH(in));	
	}
	
	 /**
	  * helper to the input stream constructor
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
	
	public void serialize(BitOutputStream out) {
		Node cur = huffQueue.peek();
		serializeH(out, cur);
	}
	
	public void encode(BitInputStream in, BitOutputStream out) {
		//first write the tree we are using to target
		this.serialize(out);
		//get the code for the data in Huffman tree and put in Hashmap
		Node cur = huffQueue.peek();
		Map<Short, ArrayList<Byte>> code = new HashMap<>();
		ArrayList<Byte> path = new ArrayList<>();
		encodeH(cur, path, code);
	}
	
	//find all path codes and put into a map
	private void encodeH(Node cur, ArrayList<Byte> path, Map<Short, ArrayList<Byte>> code) {
		
		if(cur.isLeaf()) {
			//add path data pair to code map. Then clear path for next leaf??
			code.put(cur.getData(), path);
		} else {
			//record the path taken in path list
			path.add((byte) 0);
			encodeH(cur.getLeft(), path, code);
			path.add((byte) 1);
			encodeH(cur.getLeft(), path, code);			
		}
	}

}
