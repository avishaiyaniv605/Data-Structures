
public class Page
{
	private String content;
	private Page next;
	private Page prev;
	private int index;
	
	
	public Page(String content, int index)
	{
		this.content = content;
		this.index=index;
		next=null;
		prev=null;
	}
	
	public Page getPrev()
	{
		return prev;
	}
	public void setNext(Page page)
	{
		next=page;
	}
	
	public Page getNext()
	{
		return next;
	}
	public void setPrev(Page page)
	{
		prev=page;
	}
	
	public int getIndex()
	{
		return index;
	}
	public String getContent()
	{
		return content;
	}
	public void editContent(char a)
	{
		content = content + a;
	}
	
}
