import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Spams implements Iterable<Spam>{
	
	private Spam[] _list;
	private int _size;
	private String _fileName; 
	private FileReader _fileReader;			
	private BufferedReader _bufferedReader;

	// --- Spam --- //
	/**
	 * initializes the Spams list creation
	 * @param fileName
	 */
	public void generateSpams(String fileName) {
		_fileName = fileName;
		try {
			_fileReader = new FileReader(_fileName);
			_bufferedReader = new BufferedReader(_fileReader);
			getListSize();
			_list = new Spam[_size];
			buildArray();
			_bufferedReader.close();
		} 
		catch(IOException ex) {
			System.out.println("error: unable to open file '" + _fileName + "'");
			_size = 0;
		}	
	}
	/**
	 * adds the Spam words from the file to the list
	 */
	private void getListSize() throws IOException {
		String currentWord;
		int counter = 0;
		try {
			currentWord = _bufferedReader.readLine();
			while(currentWord != null) 
			{
				counter++;		
				currentWord = _bufferedReader.readLine();
			}
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
	/**
	 * counts the lines in the txt file
	 * @return number of lines counted
	 */
	private void buildArray() {
		if (_list.length == 0) {
			System.out.println("no spam words found in '" + _fileName + "'");
			_size = 0;
			return;
		}
		String currWord;
		int currIndex = 0;
		try {
			while(currIndex < _size && (currWord = _bufferedReader.readLine()) != null) {
				String[] currSpam = currWord.split(" ");
				Spam newSpam = new Spam(currSpam[0], currSpam[1]);
				_list[currIndex] = newSpam;
				currIndex ++;
			}   
			_bufferedReader.close();  
		}
		catch(IOException ex) {
			System.out.println("error: unable to read file '"  + _fileName + "'");   
		}
	}

	// --- Iterator --- //
	@Override
	public Iterator<Spam> iterator() { return new SpamIterator(); }
	/**
	 * creates a new SpamIterator which iterates over the Spam interior array
	 */
	class SpamIterator implements Iterator<Spam>
	{
		Spam _current = null;
		int currentIndex;
		@Override
		public boolean hasNext() 
		{
			if (_size == 0 || !(currentIndex+1 < _size) || _list == null)
				return false;
			return true;
		}
		@Override
		public Spam next() 
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
}
