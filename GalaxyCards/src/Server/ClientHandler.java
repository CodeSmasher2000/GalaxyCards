package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Klass som ska ta hand om alla klienter som ansluter
 * @author Jonte
 *
 */

public class ClientHandler extends Thread{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
	
		start();
	}
}
