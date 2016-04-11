package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

import enumMessage.CommandMessage;
import enumMessage.Commands;
/**
 * Klass som ska ta hand om alla klienter som ansluter
 * @author Jonte
 *
 */

public class ClientHandler extends Thread{
	private Socket socket;
	private ServerController serverController;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	

	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	
		start();
	}
	
	public void listenForMessage(){
		String username;
		
		try {
			CommandMessage message = (CommandMessage)ois.readObject();
			if(message.getCommand() == Commands.LOGIN){
				username = message.getSender();
				serverController.login(username,this);
				
			}
		}catch(IOException e){} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void writeMessage(CommandMessage message){
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CommandMessage readMessage(){
		try {
			CommandMessage message = (CommandMessage)ois.readObject();
			return message;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}return null;
	}
	
	@Override
	public void run(){
		
		listenForMessage();
	}
}
