class LinkedList
{
	
    public Page headPage;
    public Page tailPage;
    private int size;
    
    LinkedList(int size)
    {
    	this.size = size;
    }
    public void insertFirst(Page page)
    {
    	headPage=page;
    }
    public void enqueue(Page page)
    {
    	Page tmpPage=headPage;
    	page.setNext(headPage);
    	headPage=page;
    	if (size == 1)
    		tailPage=page;
    	else
    		tmpPage.setPrev(page);
    	page.setPrev(null);
    }
    public Page dequeue()
    {
    	Page tmp = tailPage;
    	if (size == 1)
    	{
    		headPage = null;
    		tailPage = null;
    	}
    	else
    	{
    	tailPage.getPrev().setNext(null);
    	tailPage = tmp.getPrev();
    	}
    	return tmp;
    }
    public Page getHead()
    {
    	return headPage;
    }
    public void setHead(Page page)
    {
    	headPage = page;
    }
    public void setTail(Page page)
    {
    	tailPage=page;
    }
}