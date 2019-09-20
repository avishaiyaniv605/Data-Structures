
/**
 * @author  Alexander Abramov 314129438
 * 			Avishai Yaniv 307916023
 */
import java.util.Arrays;


public class MemoryManagementSystem{
	public String[] secondaryMemory;
	private boolean useLRU;
	private LinkedList mainMemory;
	private Page[] isHere;

	public MemoryManagementSystem(int mainMemorySize, int secondaryMemorySize, boolean useLRU) 
	{
		secondaryMemory = new String [secondaryMemorySize];
		mainMemory = new LinkedList(mainMemorySize);
		initateSec(secondaryMemory);
		isHere = new Page [secondaryMemorySize];
		initateMain(mainMemory, mainMemorySize);
		this.useLRU=useLRU;
	}
	public void initateSec(String[] arr)
	{
		for (int i=0; i<arr.length ; i++)
			arr[i] = "";
	}
	public void initateMain(LinkedList queue, int mainMemorySize)
	{
		Page firstPage = new Page (secondaryMemory[0],0);
		mainMemory.insertFirst(firstPage);
		isHere[0]=firstPage;
		mainMemory.setTail(firstPage);
		for (int i=1; i < mainMemorySize; i++)
		{
			Page tmpPage = new Page (secondaryMemory[i], i);
			queue.enqueue(tmpPage);
			isHere[i] = tmpPage;
		}
	}		
	@Override
	public String toString() 
	{
		return "secondaryMemory=" + Arrays.toString(secondaryMemory);
	}
	public String read(int index) 
	{
		return check(index).getContent();
	}
	public void write(int index, char c) 
	{
		check(index).editContent(c);
	}
	public Page check(int index)
	{
		if (isHere[index] != null)								//if page is in main mem, show string
		{
			Page currPage = isHere[index];
			if (useLRU)											//additional step for lru
			{
				if (currPage.getNext() == null)				//curr page is tail
				{
					mainMemory.dequeue();
					mainMemory.enqueue(currPage);
				}
				else if (currPage.getPrev() != null)
				{
					(currPage.getPrev()).setNext(currPage.getNext());
					(currPage.getNext()).setPrev(currPage.getPrev());
					mainMemory.enqueue(currPage);
				}
			}
			return currPage;
		}
		else 
		{
			Page currPage = new Page (secondaryMemory[index],index);
			Page tail = mainMemory.dequeue();
			secondaryMemory[tail.getIndex()] = tail.getContent();
			isHere[tail.getIndex()]=null;
			isHere[index]=currPage;
			mainMemory.enqueue(currPage);
			return currPage;
		}
	}

}
