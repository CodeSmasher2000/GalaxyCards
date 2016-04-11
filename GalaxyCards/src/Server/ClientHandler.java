package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

import EnumMessage.CommandMessage;
import EnumMessage.Commands;

/**
 * Klass som ska ta hand om alla klienter som ansluter
 * 
 * @author Jonte
 *
 */

public class ClientHandler extends Thread {
	private Socket socket;
	private ServerController serverController;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ClientHandler(Socket socket, ServerController serverController) throws IOException {
		this.socket = socket;
		this.serverController = serverController;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());

		start();
	}
	
	public ClientHandler getUser(){
		return this;
	}
	/**
	 * Metod som stänger klientens socket för att avsluta anslutningen.
	 */
	public void disconnect(){
		this.interrupt();
		try{
			this.socket.close();
		}catch(IOException e){}
		serverController.disconnect(this);
		System.out.println("Disconnected");
	}
	/**
	 * Metod som lyssnar efter meddelande från servern. Om meddelandet är LOGINOK är
	 * användarnamnet ledigt och klientens användarnamn läggs in i en TreeMap.
	 */
	public void listenForMessage() {
		String username;
		while (true) {
			try {
				CommandMessage message = (CommandMessage) ois.readObject();
				if (message.getCommand() == Commands.LOGIN) {
					username = message.getSender();
					serverController.login(username, this);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeMessage(CommandMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CommandMessage readMessage() {
		try {
			CommandMessage message = (CommandMessage) ois.readObject();
			return message;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void run() {

		listenForMessage();
	}
}
