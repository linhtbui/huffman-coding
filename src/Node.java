
/**
 * a basic interface to allow for InteriorNode and LeafNode connection
 * @author niehusst
 *
 */
public class Node implements Comparable<Node> {
	private Integer freq;
	private Node left;
	private Node right;
	private Short data;
	

	/**
	 * constructs an InteriorNode for HuffmanTree
	 * @param left - the Node connected on left 
	 * @param right - the Node connected on right 
	 */
	public Node(Node left, Node right) {
		this.freq = left.getFreq() + right.getFreq();
		this.right = right;
		this.left = left;
		this.data = null;
	}
	

	/**
	 * constructs a LeafNode 
	 * @param data - the character to be stored at this node
	 * @param freq - the frequency of the character in data
	 */
	public Node(Short data, Integer freq) {
		this.data = data;
		this.freq = freq;
	}
	
	
	/**
	 * getter for freq field
	 * @return - the Integer frequency as is some of the children of this Node
	 */
	public Integer getFreq() {
		return this.freq;
	}
	
	/**
	 * getter for data field
	 * @return - the Short data stored in this Node
	 */
	public Short getData() {
		return this.data;
	}
	
	
	/**
	 * tells you if Node is a LeafNode
	 * @return true - this is a LeafNode
	 */
	public Boolean isLeaf() {
		if (data == null)
			return false;
		else return true;
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
	public Node getDirection(int bin) {
		if(bin == 1) {
			return this.getRight();
		} else {
			return this.getLeft();
		}
	}

	/**
	 * helps with comparing Node objects to put them into a PriorityQueue
	 */
	@Override
	public int compareTo(Node o) {
		Node compare = (Node) o;
		if(this.getFreq() < compare.getFreq()) {
			return -1;
		} else if(this.getFreq() > compare.getFreq()) {
			return 1;
		} else {
			return 0;
		}
	}
}
