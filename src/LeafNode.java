
/**
 * a Node that is a leaf, at the end of, a Huffman tree
 * @author niehusst
 *
 */
public class LeafNode implements Node {

	private Short data;
	private Integer freq;
	
	/**
	 * constructs a LeafNode 
	 * @param data - the character to be stored at this node
	 * @param freq - the frequency of the character in data
	 */
	public LeafNode(Short data, Integer freq) {
		this.data = data;
		this.freq = freq;
	}
	
	/**
	 * getter for data field
	 * @return - the Short data stored in this Node
	 */
	public Short getData() {
		return this.data;
	}
	
	/**
	 * getter for freq field
	 * @return - the Integer frequency of the occurrence of data
	 */
	public Integer getFreq() {
		return this.freq;
	}
	
	/**
	 * tells you if Node is a LeafNode
	 * @return true - this is a LeafNode
	 */
	public Boolean isLeaf() {
		return true;
	}
}
