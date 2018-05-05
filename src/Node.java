
/**
 * a basic interface to allow for InteriorNode and LeafNode connection
 * @author niehusst
 *
 */
public interface Node {
	
	/**
	 * getter for the frequency of a Node
	 * @return - Integer, the frequency
	 */
	public Integer getFreq();
	
	/**
	 * tells you if a Node is a LeafNode
	 * @return - true if is LeafNode, else false
	 */
	public Boolean isLeaf();
}
