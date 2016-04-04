package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import EnumMessage.CommandMessage;
import EnumMessage.Commands;
/**
 * Klass som tar hand om klientens skriv och läsmetoder.
 * @author Jonte
 *
 */
public class Client {
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	

	public Client(String ip, int port) throws IOException {
		socket = new Socket(ip, port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		
	}
	/**
	 * Skickar CommandMessages 
	 * @param cmdMessage
	 * 			Det typ av Commands man skickar.
	 */			
	public void sendMessage(CommandMessage cmdMessage){
		try {
			oos.writeObject(cmdMessage);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
}
