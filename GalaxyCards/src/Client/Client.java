package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import Server.ServerController;

import javax.swing.JOptionPane;

import enumMessage.CommandMessage;
import enumMessage.Commands;



/**
 * Klass som skapar en klients strömmar  och tar hand om klientens skriv och läsmetoder.
 * @author Jonte
 *
 */
public class Client {
	private Socket socket;
	private ClientController controller;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Listener listener;


	

	public Client(String ip, int port, ClientController clientController) {
		try {
			this.controller = clientController;
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.listener = new Listener();
		this.listener.start();
		
	}
	
	/**
	 *Metod som avbryter Listener-tråden och stänger klientens socket.
	 */
	public void disconnect(){
		try{
			this.listener.interrupt();
			this.socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public CommandMessage readMessage() {
		try {
			CommandMessage respone =  (CommandMessage)ois.readObject();
			return respone;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Skickar CommandMessages 
	 * @param cmdMessage
	 * 			Meddelande som ska skickas
	 */			
	public void sendMessage(CommandMessage cmdMessage){
		try {
			oos.writeObject(cmdMessage);
			oos.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Metod som lyssnar efter CommandMessage från servern. Beroende på vilket command som finns 
	 *  i meddelandet anropas olika metoder.
	 */
	public void listenForMessage(){
		try {
			CommandMessage message = (CommandMessage)ois.readObject();
			message.toString();
			if(message.getCommand()==Commands.LOGIN){
				System.out.println("Login");
				controller.login();
			}else if(message.getCommand()==Commands.GETHERO){
				System.out.println("GetHero");
				controller.setHero(message);
			} else if(message.getCommand() == Commands.MATCHMAKING_MATCH_FOUND) {
				System.out.println("Match Found");
				controller.matchFound(message);
			} else if(message.getCommand() == Commands.MATCH_PLAYCARD); {
				controller.cardPlayed(message);
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("User Disconnected");
			e.printStackTrace();
		}
	}
	
//	public void setClientController(ClientController controller) {
//		this.controller = controller;
//		controller.setClient(this);
//	}
	
	/**
	 * Klass som ärver Thread. Låter klienten logga in och lyssnar sedan efter meddelanden från servern.
	 * @author Jonte
	 *
	 */
	private class Listener extends Thread {
		@Override
		public void run() {
			System.out.println("Klient: Ansluten Till Server");
			controller.login();
				while (!socket.isClosed()) {
					listenForMessage();
				}
		}
	}
	
	
	
}
