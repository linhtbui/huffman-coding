
/**
 * a Node that is on the interior of a Huffman tree
 * @author niehusst
 *
 */
public class InteriorNode implements Node {

	private Integer freq;
	private Node left;
	private Node right;
	
	/**
	 * constructs an InteriorNode for HuffmanTree
	 * @param left - the Node connected on left 
	 * @param right - the Node connected on right 
	 */
	public InteriorNode(Node left, Node right) {
		this.freq = left.getFreq() + right.getFreq();
		this.right = right;
		this.left = left;
	}
	
	/**
	 * getter for freq field
	 * @return - the Integer frequency as is some of the children of this Node
	 */
	public Integer getFreq() {
		return this.freq;
	}
	
	/**
	 * getter for right field
	 * @return - the Node right
	 */
	public Node getRight() {
		return this.right;
	}
	
	/**
	 * getter for left field
	 * @return - the Node left
	 */
	public Node getLeft() {
		return this.left;
	}
	
	/**
	 * returns the node from either left or right, as indicated by bin. (For serealization)
	 * @param bin - a binary number (0 or 1). 0 indicates left, 1 indicates right
	 * @return - a Node, either left or right
	 */
	public Node getDirection(byte bin) {
		if(bin == 1) {
			return this.getRight();
		} else {
			return this.getLeft();
		}
	}
	
	/**
	 * tells you if Node is LeafNode
	 * @return false - this isn't a LeafNode
	 */
	public Boolean isLeaf() {
		return false;
	}
}
