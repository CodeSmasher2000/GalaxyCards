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
 * Klass som skapar en ny klient och tar hand om klientens skriv och läsmetoder.
 * @author Jonte
 *
 */
public class Client {
	private Socket socket;
	private ClientController controller;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Listener listener;


	

	public Client(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.listener = new Listener();
		this.listener.start();
		
	}
	
	public void disconnect(){
		try{
			this.socket.close();
		}catch(IOException e){}
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
	
	public CommandMessage readMessage(){
		try {
			CommandMessage response = (CommandMessage)ois.readObject();
			return response;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}return null;
		
	}
	
	public void listenForMessage(){
		try {
			CommandMessage message = (CommandMessage)ois.readObject();
			if(message.getCommand()==Commands.LOGIN){
				controller.login();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setClientController(ClientController controller) {
		this.controller = controller;
		controller.setClient(this);
	}
	
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
				while (true) {
					listenForMessage();
				}
		}
	}
	
	
	
}
