
package enumMessage;

import java.io.Serializable;

/**
 * The class is used to send messages between the server and the client. It uses
 * a Enum Commands to decide what type of message it is.
 * 
 * @author Jonte, Patrik Larsson
 *
 */
public class CommandMessage implements Serializable {
	public String sender;
	private Commands commands;
	private Object data;
	private static final long serialVersionUID = 42L;

	/**
	 * Creates a new comands message with a command and a sender
	 * 
	 * @param commands
	 *            The command to send
	 * @param sender
	 *            A String represetning who sent the message
	 */
	public CommandMessage(Commands commands, String sender) {
		this.sender = sender;
		this.commands = commands;
	}

	/**
	 * The Creates a Command message with a command , a sender and a objerct
	 * 
	 * @param commands
	 *            The type of command the message represents
	 * @param sender
	 *            A String representing who sent the message
	 * @param data
	 *            A object that is a reference to the data that the message
	 *            should contain.
	 */
	public CommandMessage(Commands commands, String sender, Object data) {
		this(commands, sender);
		this.setData(data);
	}

	/**
	 * Sets the sender of the message
	 * 
	 * @param sender
	 *            A String representing the sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Returns the sender of the CommandMessage object
	 * 
	 * @return A String with who sent the message
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * The comand of the message
	 * 
	 * @return A Command
	 */
	public Commands getCommand() {
		return this.commands;
	}

	/**
	 * Returns the data of the object
	 * 
	 * @return If the message contains a object it returns a refernce to that
	 *         object. If not the method returns null.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data object of the message
	 * 
	 * @param data
	 *            A Reference to the object to send with the message
	 */
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return this.commands.name();
	}

}
