package Server;

public class Server {
	private Connection connection;
	private ClientHandler clientHandler;
	
	public static void main(String[] args) {
		new Connection(3550);
	}
}
