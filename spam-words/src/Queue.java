class Queue
{
	int _front, _rear, _size;
	int  _capacity;
	BTreeNode _array[];

	public Queue(int capac) {
		_capacity = capac;
		_front = _size = 0;
		_rear = _capacity - 1;
		_array = new BTreeNode[_capacity];
	}

	/**
	 * Queue is full when size becomes equal to the capacity 
	 * @return
	 */
	public boolean isFull()
	{  
		return _size == _capacity;
	}

	/**
	 * Queue is empty when size is 0
	 * @return
	 */
	public boolean isEmpty()
	{  
		return _size == 0; 
	}

	/**
	 * Method to add an item to the queue
	 * It changes rear and size
	 * @param item
	 */
	public void enqueue( BTreeNode item)
	{
		if (isFull())
			return;
		_rear = (_rear + 1)%_capacity;
		_array[_rear] = item;
		_size ++;
	}

	/**
	 * Method to remove an item from queue
	 * It changes front and size
	 * @return
	 */
	public BTreeNode dequeue()
	{
		if (isEmpty())
			return null;

		BTreeNode item = _array[_front];
		_front = (_front + 1)%_capacity;
		_size --;
		return item;
	}

}