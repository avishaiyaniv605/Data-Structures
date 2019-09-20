public class Message 
{
	private String _receiver;
	private String _sender;
	private String _content;
	
	/**
	 * this constructor initializes a Message object with given strings
	 * @param sender is the person who sends the message
	 * @param receiver is the person who gets the message
	 * @param content is the message text delivered by the sender to the receiver
	 */
	public Message(String sender, String receiver, String content) 
	{
		_receiver = receiver;
		_sender = sender;
		_content = content;
	}
	
	// --- Getters --- //
	public String getContent()
	{
		return _content;
	}
	public String getSender()
	{
		return _sender;
	}
	public String getReceiver()
	{
		return _receiver;
	}
}
