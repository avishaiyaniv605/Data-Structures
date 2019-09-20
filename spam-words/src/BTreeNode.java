
public class BTreeNode {
	private int _n;             //number of keys in node
	private boolean _leaf;  	//true if node is a leaf
	private int _t;             //minimum degree
	private String[] _keys;
	private BTreeNode[] _children;
	private BTreeNode _parent;
	private int _height;

	public BTreeNode(int t) {
		_n = 0;
		_t = t;
		_leaf = true;
		_children = new BTreeNode[2*_t];
		_keys = new String[2*t - 1];
	}

	
	// --- Setters --- //
	/**
	 * sets the "is leaf" status of the node
	 * @param leaf
	 */
	public void setLeaf(boolean leaf) {
		_leaf = leaf;
	}
	/**
	 * sets the parent of the node
	 * @param parent
	 */
	public void setParent(BTreeNode parent)
	{
		_parent = parent;
	}
	/**
	 * sets the height of the node in the BTree
	 * @param dep
	 */
	public void setHeight(int dep)
	{
		_height = dep;
	}
	/**
	 * sets the number of keys in the node
	 * @param n
	 */
	public void set_n(int n) {
		_n = n;
	}
	/**
	 * sets a child in a given index
	 * @param index is the index to set a given child
	 * @param x is a child to set in the given index
	 */
	public void setChildInPlace(int index, BTreeNode x){
		if(index < _children.length){
			_children[index] = x;
		}
	}
	/**
	 * sets a key in a given index
	 * @param index is an index to put a key in
	 * @param str is a key to put in the given index
	 */
	public void setKeyInPlace(int index, String str){
		if(index < _keys.length){
			_keys[index] = str;
		}
	}

	// --- Getters --- //
	/**
	 * checks whether the node is leaf or not
	 * @return true if node is a leaf, either returns false
	 */
	public boolean is_leaf() {
		return _leaf;
	}
	/**
	 * gets the number of keys in node
	 * @return number of keys in node
	 */
	public int getN() {
		return _n;
	}
	/**
	 * gets the key in the specified given index
	 * @param index is an integer to get a key from the node
	 * @return a key in the index
	 */
	public String getKeyInPlace(int index) {
		if (index < _keys.length) {
			return _keys[index];
		}
		return null;
	}
	/**
	 * gets the child in the specified given index
	 * @param  index is an integer to get a child from the node's children
	 * @return a child in the index
	 */
	public BTreeNode getChild(int index){
		if(index < _children.length){
			return _children[index];
		}
		return null;
	}
	/**
	 * gets the keys in the node
	 * @return an array of Strings represent keys
	 */
	public String[] getKeys(){
		return _keys;
	}
	/**
	 * gets the children in the node
	 * @return an array of BTreeNodes
	 */
	public BTreeNode[] getChildren(){
		return _children;
	}
	/**
	 * gets the node's height
	 * @return
	 */
	public int getHeight()
	{
		return _height;
	}
	/**
	 * gets the node's parent
	 * @return
	 */
	public BTreeNode getParent()
	{
		return _parent;
	}
}
