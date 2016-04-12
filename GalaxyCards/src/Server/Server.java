package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klass som startar upp en server med hjälp av inre klassen Connection.
 * @author Jonte
 *
 */

public class Server {
	private ServerController serverController = new ServerController();
	private ClientHandler clientHandler;
	
	public Server(){
		new Connection(3550).start();
	}
	

	public ServerController getServerController(){
		return serverController;
	}
	/**
	 * Klass som startar upp serverns socket och ClientHandler objekt.
	 * @author Jonte
	 *
	 */
	private class Connection extends Thread {
		private int port;
	
		
		/**
		 * Konstruktor 
		 * @param port
		 * 			Porten som servern lyssnar efter ny anslutning på.
		 */
		public Connection(int port) {
			this.port = port;
		}
		
		/**
		 * Metod som skapar en ny ServerSocket med angiven port och låter klienter ansluta till den.
		 */
		public void connect(){
			Socket socket = null;
			System.out.println("Server: Server started");
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while (true) {
					try {
						socket = serverSocket.accept();
						// Loggas när någon ansluter
						
						new ClientHandler(socket,serverController);
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

	
	
	
	
	
	
	
	
}
