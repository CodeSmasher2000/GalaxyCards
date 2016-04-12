package Server;

/**
 * This class contatins nessarsacry data and methods for storing data about
 * a match
 * @author patriklarsson
 *
 */
public class Match {
	private ClientHandler user1;
	private ClientHandler user2;
	
	/**
	 * The constructor sets up a Match object with two clientHandlers that are
	 * playing the match.
	 * 
	 * @param user1
	 * 		A Reference to a clienthandler object.
	 * @param user2
	 * 		A Reference to a clienhandler object.
	 */
	public Match(ClientHandler user1, ClientHandler user2) {
		this.user1 = user1;
		this.user2 = user2;
		System.out.println("Server: Match Started");
	}
}
