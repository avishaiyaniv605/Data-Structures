import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Messages implements Iterable<Message>
{
	private Message[] _list;
	private int _size = 0;
	private String _fileName; 				
	private FileReader _fileReader;			
	private BufferedReader _bufferedReader;
	private HashTable[] _messagesHashTable;

	// --- Message --- //
	/**
	 * initializes the Messages list creation
	 * @param fileName
	 */
	public void generateMessages(String fileName)
	{
		_fileName = fileName;
		try {
			_fileReader = new FileReader(_fileName);
			_bufferedReader = new BufferedReader(_fileReader);
			getListSize();
			_list = new Message[_size];
			buildArray();
			if (_list.length == 1) {
				System.out.println("no messages found in '" + _fileName + "'");
				_size = 0;
				return;
			}
			_bufferedReader.close();
		} 
		catch(IOException ex) {
			System.out.println("error: unable to open file '" + _fileName + "'");
			_size = 0;
		}		
	}
	/**
	 * adds the Messages from the file to the list
	 */
	private void buildArray() {
		String currentMessage;
		int msgNumber = 0;
		try {
			while(msgNumber < _size && (currentMessage = _bufferedReader.readLine()) != null) {
				if (currentMessage.isEmpty())
					continue;
				String sender = currentMessage.substring(5);
				String receiver = _bufferedReader.readLine().substring(3);
				String message = _bufferedReader.readLine();
				while ((currentMessage = _bufferedReader.readLine()) != null && !currentMessage.equals("#"))
					message += '\n' + currentMessage;
				_list [msgNumber] = new Message (sender,receiver,message);
				msgNumber++;
			}   
			_bufferedReader.close();  
		}
		catch(IOException ex) {
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
	}
	/**
	 * counts the times '#' appears in the txt file
	 * @return number '#' is written
	 */
	private void getListSize() throws IOException {
		String currentMessage;
		int counter = 0;
		try {
			while((currentMessage = _bufferedReader.readLine()) != null) 
				if (currentMessage.equals("#"))
					counter++;
			counter++;
		}
		catch(IOException ex) {
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
		if (counter > 0)
			_size = counter;
		// we have popped all lines in the file, so we must reopen it
		_fileReader = new FileReader(_fileName);
		_bufferedReader = new BufferedReader(_fileReader);
	}

	// --- HashList --- //
	/**
	 * creates a hash table based on the words in the message.
	 */
	public void createHashTables(String mString)
	{
		int m = 0;
		try {  
			m = Integer.parseInt(mString);  
		} catch (NumberFormatException e) {  
			System.out.println("invalid number");
			return;  
		} 
		_messagesHashTable = new HashTable[_size];
		int currTable = 0;
		Iterator<Message> mItr = iterator();
		while (mItr.hasNext())
		{
			_messagesHashTable[currTable] = new HashTable(m);
			Message curr = (Message) mItr.next();
			String[] splittedMsg = splitMessage(curr);
			for (int i=0; i<splittedMsg.length; i++)
				_messagesHashTable[currTable].insert(splittedMsg[i]);
			currTable ++;
		}	
	}
	/**
	 * splits a message into an array of strings which represents the message's word
	 * @param curr
	 * @return
	 */
	private String[] splitMessage(Message curr) {
		String msg = curr.getContent();
		String[] words = msg.split(" |\n");
		return words;
	}

	// --- Spam --- //
	/**
	 * finds the spam messages in the messages list 
	 * @param fileName is a list of spam words
	 * @param bTree a tree which holds the friendships
	 * @return
	 */
	public String findSpams(String fileName,BTree bTree) {
		Spams spams = new Spams();
		spams.generateSpams(fileName); 
		Iterator<Spam> sItr;
		Iterator<Message> mItr = iterator();
		String spamLines = "";					//ans string
		int line = 0;
		while (mItr.hasNext()) {
			Message currMsg = mItr.next();
			if (!areFriends(bTree,currMsg)) {
				boolean isSpam = false;
				sItr = spams.iterator();
				while (sItr.hasNext() && !isSpam)
				{
					Spam currSpam = sItr.next();
					String spamWord = currSpam.getWord();
					int spamPerc = currSpam.getPercent();
					isSpam = checkSpam(line,spamWord,spamPerc);
				}
				spamLines += addLine(isSpam,spamLines,line);
			}
			line ++;			
		}
		return spamLines;
	}
	/**
	 * adds the line to the spam lines string
	 * @param isSpam determines whether the current message is spam or not
	 * @param spamLines the string of spam messages
	 * @param line is the current line which is going to be added or not to the string
	 * @return
	 */
	private String addLine(boolean isSpam, String spamLines,int line) {
		if (!isSpam)
			return "";
		if (spamLines.isEmpty())
			return ""+line;
		else 
			return "," + line;
	}
	/**
	 * checks whether the sender and receiver of a given message are friends.
	 * @param bTree is a tree which holds the relationships
	 * @param msg is a message to check the sender and receiver
	 * @return
	 */
	private boolean areFriends(BTree bTree,Message msg) {
		String currSender = msg.getSender();
		String currReceiver = msg.getReceiver();
		boolean check1 = bTree.search(currSender + " & " + currReceiver);
		boolean check2 = bTree.search(currReceiver + " & " + currSender);
		return check1 || check2;
	}
	/**
	 * checks if a spam word repeats in a message more than allowed times 
	 * @param currMsg is the message to check
	 * @param spam is a word to check if appears in the message
	 * @param number is the max allowed number that the spam word can appear in the message
	 * @return
	 */
	private boolean checkSpam(int currMsg,String spam, int percAllowed) {
		HashListElement currElem = _messagesHashTable[currMsg].search(spam);
		if (currElem != null)
		{
			double numOfWords = _messagesHashTable[currMsg].getN();	//num of words in curr msg
			double numOfReps = currElem.getCounter();					//num of reps of spam word in the msg
			double repsPercent = numOfReps/numOfWords;
			return repsPercent*100 > percAllowed;
		}
		return false;
	}

	// --- Iterator --- //
	@Override
	public Iterator<Message> iterator() { return new MessageIterator(); }
	/**
	 * creates a new MessageIterator which iterates over the Messages interior array
	 */
	class MessageIterator implements Iterator<Message>
	{
		Message _current = null;
		int currentIndex;
		@Override
		public boolean hasNext() 
		{
			if (_size == 0 || !(currentIndex+1 < _size) || _list == null)
				return false;
			return true;
		}
		@Override
		public Message next() 
		{
			if (_current == null && _list != null)
			{
				_current = _list[0];
				currentIndex = 0;
				return _current;
			}
			else if (_current != null && currentIndex +1 < _size)
			{
				currentIndex ++;
				_current = _list[currentIndex];
				return _current;
			}
			throw new NoSuchElementException();
		}
	}


	//FOR TESTING
	/**
	 *  prints all messages in the given order, including '#'
	 */
	public String toString()
	{
		if (_size == 0)
			return "error: no messages found";
		String ans = "";
		for (int i=0; i<_size;i++)
		{
			if (i != _size-1)
				ans += _list[i].toString() + '\n' + "#"  + '\n';
			else
				ans += _list[i].toString();
		}
		return ans;
	}

}
