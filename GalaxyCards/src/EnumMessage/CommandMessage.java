package enumMessage;
	/**
	 * Klass som hanterar de olika Commandsen man skickar och tar emot.
	 * @author Jonte
	 *
	 */
public class CommandMessage {
	public String sender;
	private Commands commands;

	
	public CommandMessage(Commands commands, String sender){
		this.commands=commands;
		this.sender= sender;
	}
	
	public void setSender(String sender){
		this.sender = sender;
	}
	
	public String getSender(){
		return sender;
	}
	
	public Commands getCommand(){
		return this.commands;
	}
	
	
	
	
		
	
	
}
