package Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cards.HeroicSupport;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import game.Hero;
import guiPacket.Card;

/**
 * This class contatins nessarsacry data and methods for storing data about
 * a match. It´s a thread since we 
 * @author patriklarsson
 *
 */
public class Match implements Observer {
	private ClientHandler user1;
	private ClientHandler user2;
	private Player player1;
	private Player player2;
	private int id;
	
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
		this.user1.addObserver(this);
		this.user2.addObserver(this);
		System.out.println("Server: Match Started");
		player1 = new Player(user1);
		player2 = new Player(user2);
	}
	
	/**
	 * Adds the card to the correct player and the correct lane and sends a
	 * message to the other player
	 */
	public void cardPlayed() {
		
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Jag har ändradts");
	}

	/**
	 * Contatins three lists representing the differnet lanes in a gameboard
	 * 
	 * @author patriklarsson
	 *
	 */
	private class Player {
		private String name;
		private List<HeroicSupport> HeroicSupportLane = new LinkedList<HeroicSupport>();
		private List<Card> defensiveLane = new LinkedList<Card>();
		private List<Card> offensiveLane = new LinkedList<Card>();
		private Hero hero;
		private List<Card> scarpYard = new ArrayList<Card>();
		private ClientHandler clientHandler;
		
		public Player(ClientHandler clientHandler) {
			this.clientHandler = clientHandler;
			this.name = clientHandler.getActiveUser();
			// TODO Ask the client for what hero it plays with
			
		}
		
		public void playCard(Card card) {
			
		}
		
	}
	
	
	
}
