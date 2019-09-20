import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BTree {
	private Queue _bfsQueue;
	private BTreeNode _root;
	private int _t;
	private String[] _friends;
	private int _treeSize;
	private String _fileName;
	private FileReader _fileReader;
	private BufferedReader _bufferedReader;


	public BTree(String t) {
		try {  
			_t = Integer.parseInt(t);
		} catch (NumberFormatException e) {  
			System.out.println("invalid number");
			return;  
		} 
	}
	/**
	 * initializes the tree creation process
	 * @param path is a string represents path to the file to read from
	 */
	public void createFullTree(String path) {
		_fileName = path;
		initiatingFileHandler();
		_root = new BTreeNode(_t);
		_root.setHeight(0);
		getTreeSize();
		_friends = new String[_treeSize];
		initiatingFileHandler();
		getFriendsFromFile();
		insertFriends();
		_bfsQueue = new Queue(_treeSize);
	}

	/**
	 * searches for the given String in the B-Tree using the recursive method
	 * @param key is string represents a couple of friends to find in the tree
	 * @return true whether the people given in the string are friends, either returns false
	 */
	public boolean search(String key) {
		if (_root == null) {
			return false;
		}
		return search(key, _root);
	}
	private boolean search(String key, BTreeNode x) {
		int i = 0;
		while (i < x.getN() && key.compareTo(x.getKeyInPlace(i)) > 0) { //if key is bigger than current
			i++;
		}
		if (i < x.getN() && key.compareTo(x.getKeyInPlace(i)) == 0) { //if found
			return true;
		}
		if (x.is_leaf()) { //if not found
			return false;
		} else {
			return search(key, x.getChild(i));
		}
	}

	/**
	 * reads the file, prints error if file is not found
	 */
	private void initiatingFileHandler() {
		try {
			_fileReader = new FileReader(_fileName);
			_bufferedReader = new BufferedReader(_fileReader);
		} catch (IOException ex) {
			System.out.println("error: unable to open file '" + _fileName + "'");
			_treeSize = 0;
		}
	}
	/**
	 * finds the number of friends list
	 */
	private void getTreeSize() {
		String currentPairOfFriends;
		int counter = 0;
		try {
			currentPairOfFriends = _bufferedReader.readLine();
			while (currentPairOfFriends != null) {
				counter++;
				currentPairOfFriends = _bufferedReader.readLine();
			}
		} catch (IOException ex) {
			System.out.println("error: unable to read file '" + _fileName + "'");
		}
		if (counter > 0) {
			_treeSize = counter;
		}
	}
	/**
	 * gets friends from file onto the tree
	 */
	private void getFriendsFromFile() {
		if (_friends.length == 0) {
			_treeSize = 0;
			System.out.println(_fileName + "will generate an empty tree ");
			return;
		}
		String currentPairOfFriends;
		int counter = 0;
		try {
			while ((currentPairOfFriends = _bufferedReader.readLine()) != null) {
				_friends[counter] = currentPairOfFriends;
				counter++;
			}
			_bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("error: unable to read file '" + _fileName + "'");
		}
	}
	/**
	 * run over the friends array and inserts the friends using the B-Trre insert method 
	 */
	private void insertFriends() {
		for (int i = 0; i < _friends.length; i++) {
			insert(_friends[i]);
		}
	}
	private void insert(String k) {
		BTreeNode r = _root;
		/*
        if node is full, create a new node to be the new root and set it as father.
		 */
		if (r.getN() == 2 * _t - 1) {
			BTreeNode s = new BTreeNode(_t);
			s.setLeaf(false);
			s.setChildInPlace(0, r);
			r.setParent(s);
			s.setHeight(r.getHeight()+1);
			_root = s;
			splitChild(s, 0, r);
			r = s;
		}
		insertNonFull(r, k);
	}
	/**
	 * splits children in the node
	 * @param x
	 * @param i
	 * @param y
	 */
	private void splitChild(BTreeNode x, int i, BTreeNode y) {
		BTreeNode z = new BTreeNode(_t);
		z.setHeight(y.getHeight());
		z.setParent(x);
		z.setLeaf(y.is_leaf());
		z.set_n(_t - 1);
		for (int j = 0; j < _t - 1; j++) {
			z.setKeyInPlace(j, y.getKeyInPlace((j + _t)));
			y.setKeyInPlace(j + _t, null); //just for conveniently
		}
		if (!y.is_leaf()) {
			for (int k = 0; k < _t; k++) {
				z.setChildInPlace(k, y.getChild((k + _t)));
				z.getChild(k).setParent(z);	// update every new y's son parent pointer
				y.setChildInPlace(k + _t, null); //just for conveniently
			}
		}
		y.set_n(_t - 1);
		for (int j = x.getN(); j >= i; j--) {
			x.setChildInPlace(j + 1, x.getChild(j));
		}
		x.setChildInPlace(i + 1, z);
		for (int j = x.getN() - 1; j >= i; j--) {
			x.setKeyInPlace(j + 1, x.getKeyInPlace(j));
		}
		x.setKeyInPlace(i, y.getKeyInPlace(_t - 1));
		y.setKeyInPlace(_t - 1, null);
		x.set_n(x.getN() + 1);
	}
	/**
	 * inserts a node into the tree
	 * @param x
	 * @param key
	 */
	private void insertNonFull(BTreeNode x, String key) {
		int i = x.getN() - 1;
		if (x.is_leaf()) { // if node is leaf, just find the place to add and add
			while (i >= 0 && key.compareTo(x.getKeyInPlace(i)) < 0) {
				x.setKeyInPlace(i + 1, x.getKeyInPlace(i));
				i--;
			}
			x.setKeyInPlace(i + 1, key);	
			x.set_n(x.getN() + 1);
		} else {
			/*if node is not a leaf recursively continue and find the right node.
			 *split in case needed
			 */
			while (i >= 0 && key.compareTo(x.getKeyInPlace(i)) < 0) {
				i--;
			}
			i++;
			if (x.getChild(i).getN() == 2 * _t - 1) { // if node is full
				splitChild(x, i, x.getChild(i));
				if (key.compareTo(x.getKeyInPlace(i)) > 0) {
					i++;
				}
			}
			insertNonFull(x.getChild(i), key); // recursive call
		}
	}

	/**
	 * scanning the B-Tree using the parseSign method to calculate the sign needed to add 
	 * @return string of the tree scanned vertically
	 */
	private String BFS() {
		String ans = "";
		if (_root != null) 
			_bfsQueue.enqueue(_root);
		int height = _root.getHeight();
		boolean newHeight = false;
		while (!(_bfsQueue.isEmpty())) {
			BTreeNode x = _bfsQueue.dequeue();
			int currHeight = x.getHeight();
			if (currHeight != height)
			{
				height = currHeight;
				newHeight = true;
			}
			else 
				newHeight = false;
			for (int i = 0; i < x.getKeys().length; i++) 
				if (x.getKeyInPlace(i) != null)
					ans += parseSign(i,newHeight,x) + x.getKeyInPlace(i);
			for (int i = 0; i < x.getChildren().length; i++) 
				if (x.getChild(i) != null) 
					_bfsQueue.enqueue(x.getChild(i));
		}
		return ans;
	}
	/**
	 * parses which sign needs to be added to the string according given node 
	 * @param i is the node's index in the parent's nodes array
	 * @param newDepth specifies whether the node is lower than the previous one
	 * @param x is the current node
	 * @return
	 */
	private String parseSign(int i, boolean newDepth, BTreeNode x) {
		if (x == null)
			return "";
		if (i == 0)
		{
			if (_root == x)
				return "";
			if (newDepth)							//previous is higher
				return "#";
			if(x.getParent().getChild(i) != x)		//if the node is not the left one so the parent has more children on the left side
				return "|";
			else 
				return "^";
		}
		return ",";
	}
	@Override
	public String toString()
	{
		return BFS();
	}

}
