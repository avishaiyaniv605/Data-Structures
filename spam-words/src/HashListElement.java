public class HashListElement {
	private String _word;
	private HashListElement _next;
	private int _counter;
	
	public HashListElement(String word) {
		_word = word;
		_counter = 1;
	}

	/**
	 * updates the times the word appears in the message
	 */
	public void updateCounter() {
		_counter++;		
	}

	/**
	 * sets the next node appears after this in the list
	 * @param nxt
	 */
	public void setNext(HashListElement nxt) {
		_next = nxt;
	}

	// --- Getters --- //
	/**
	 * gets the times the word appears in the messages
	 * @return
	 */
	public int getCounter() {
		return _counter;
	}
	/**
	 * gets the word
	 * @return
	 */
	public String getWord() {
		return _word;
	}
	/**
	 * gets the next node in the list
	 * @return
	 */
	public HashListElement getNext() {
		return _next;
	}
	
}
