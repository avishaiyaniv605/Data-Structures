public class HashList {

	private HashListElement _head;
	public HashList() {}

	/**
	 * inserts a new Element onto the list
	 * @param word
	 */
	public void insert(String word) {
		HashListElement newWord = new HashListElement (word);
		newWord.setNext(_head);
		_head = newWord;
	}
	/**
	 * checks if the list contains an element with a given word
	 * @param word
	 * @return
	 */
	public boolean contains(String word) {
		HashListElement elem = get(word);
		if (elem == null)
			return false;
		return true;
	}
	/**
	 * updates the element in the list which holds the same word as given
	 * @param word
	 */
	public void updateCounter(String word) {
		HashListElement elem = get(word);
		if (elem == null)
			return;
		elem.updateCounter();
	}

	/**
	 * finds an elements in the list which holds the same word as given
	 * @param word
	 * @return null if is not found
	 */
	public HashListElement get(String word) {
		HashListElement currElem = _head;
		while (currElem != null) 
		{
			if (currElem.getWord().toLowerCase().equals(word.toLowerCase()))
				return currElem;
			currElem = currElem.getNext();
		}
		return null;
	}
	/**
	 * return the times a word appears in the message
	 * @param word
	 * @return
	 */
	public int getRepsNumber(String word)
	{
		HashListElement elem = get(word);
		if (elem != null)
		{
			return elem.getCounter();
		}
		return -1;
	}

}
