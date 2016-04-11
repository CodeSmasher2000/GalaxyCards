package EnumMessage;

import java.io.Serializable;

/**
	 * Klass som hanterar de olika Commandsen man skickar och tar emot.
	 * @author Jonte
	 *
	 */
public class CommandMessage implements Serializable {
	public String sender;
	private Commands commands;
	private static final long serialVersionUID = 42L;
	
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
