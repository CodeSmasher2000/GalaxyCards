package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import enumMessage.CommandMessage;
import enumMessage.Commands;

/**
 * Klass som ska ta hand om alla klienter som ansluter
 * 
 * @author Jonte, Patrik Larsson
 *
 */

public class ClientHandler extends Observable implements Runnable {
	private Socket socket;
	private ServerController serverController;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String activeUser = null;
	private Thread listenerThread;

	public ClientHandler(Socket socket, ServerController serverController) throws IOException {
		this.socket = socket;
		this.serverController = serverController;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		
		// Starts a thread that is listening for incomming messages.
		listenerThread = new Thread(this);
		listenerThread.start();
	}
	
	
	public String getActiveUser() {
		return activeUser;
	}
	
	/**
	 * Sätter activeUser till det användarnamn som matats in när en klient loggar in.
	 * @param userName
	 * 			Det inmatade användarnamnet
	 */
	public void setActiveUser(String userName) {
		this.activeUser = userName;
	}

	public ClientHandler getUser() {
		return this;
	}

	/**
	 * Metod som stänger klientens socket för att avsluta anslutningen.
	 */
	public void disconnect() {
		listenerThread.interrupt();
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverController.disconnect(this);
		System.out.println("Disconnected");
	}

	/**
	 * Metod som lyssnar efter meddelande från servern. Om meddelandet är
	 * LOGINOK är användarnamnet ledigt och klientens användarnamn läggs in i en
	 * TreeMap.
	 * 
	 * @throws IOException
	 */
	public void listenForMessage() throws IOException {
		String username;
		while (true) {
			try {
				CommandMessage message = (CommandMessage) ois.readObject();
				switch (message.getCommand()) {
				case LOGIN:
					username = message.getSender();
					serverController.login(username, this);
					break;
				case GETHERO:
					serverController.sendHero(this);
					break;
				case MATCHMAKING_START:
					serverController.addUserToMatchMaking(activeUser);
					break;
				case MATCH_PLAYCARD:
					System.out.println("Hej");
					setChanged();
					notifyObservers(message);
					break;
				case MATCH_DRAW_CARD:
					setChanged();
					notifyObservers(message);
					break;
				default:
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Metod som skickar CommandMessage till klienten
	 * @param message
	 * 			Det meddelande som ska skickas
	 */
	public void writeMessage(CommandMessage message) {
		try {
			System.out.println(message.toString());
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metod som läser meddelande från klienten.
	 * @return
	 * 		CommandMessage från klienten.
	 */
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

		try {
			listenForMessage();
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
			listenerThread.interrupt();
		}
	}
	
	
	
	
}
