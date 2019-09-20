public class HashTable {

	private HashList[] _hashList;
	private final double _A = ((Math.sqrt(5))-1)/2;
	private int _m;
	private int _n = 0;		//number of object in the table
	
	public HashTable(int m) 
	{
		_hashList = new HashList[m];
		_m = m;
	}
	/**
	 * inserts a new list onto the table
	 * @param word
	 */
	public void insert(String word) {
		int index = hash(word);
		if (_hashList[index] != null && _hashList[index].contains(word))
		{
			_hashList[index].updateCounter(word);
		}
		else if (_hashList[index] != null)
		{
			_hashList[index].insert(word);
		}
		else
		{
			HashList newList = new HashList();
			newList.insert(word);
			_hashList[index] = newList;
		}
		_n++;
	}
	/**
	 * searches for a given word in the table,
	 * if found, looks for the word in the list
	 * @param word
	 * @return
	 */
	public HashListElement search (String word)
	{
		int index = hash(word);
		HashList searchList = _hashList[index];
		if (searchList == null)
			return null;
		return searchList.get(word);
	}
	/**
	 * hash function to find the index of the given word
	 * @param word
	 * @return
	 */
	private int hash(String word)
	{
		String check = word.toLowerCase();
		double k = Math.abs(check.hashCode());
		return (int) (_m * ((k*_A) %1));
	}
	/***
	 * gets the number of word in the table
	 * @return
	 */
	public int getN()
	{
		return _n;
	}
}
