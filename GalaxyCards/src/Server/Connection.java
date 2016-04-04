package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klass som ska ta hand om när en klient försöker ansluta eller avlsuta anslutning.
 * @author Jonte
 *
 */

public class Connection extends Thread {
	private int port;
	private ClientHandler clientHandler;
	/**
	 * Konstruktor 
	 * @param port
	 * 			Porten som servern lyssnar efter ny anslutning på.
	 */
	public Connection(int port) {
		this.port = port;
	}
	
	public void disconnect(){
		
	}
	
	public void connect(){
		Socket socket = null;
		System.out.println("Server: Server started");
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				try {
					socket = serverSocket.accept();
					// Loggas när någon ansluter
					
					new ClientHandler(socket);
				} catch (IOException e) {
					System.err.println(e);
					if (socket != null)
						socket.close();

				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("Server: Server is stopped");
	}
	

	public void run() {
		connect();
	}
}
