
package enumMessage;
import java.io.Serializable;



	/**
	 * Klass som hanterar de olika Commandsen man skickar och tar emot.
	 * @author Jonte
	 *
	 */
public class CommandMessage implements Serializable {
	public String sender;
	private Commands commands;
	private Object data;
	private static final long serialVersionUID = 42L;
	
	public CommandMessage(Commands commands, String sender){
		this.sender = sender;
		this.commands = commands;
	}
	
	public CommandMessage(Commands commands, String sender, Object data){
		this(commands, sender);
		this.setData(data);
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	
		
	
	
}
