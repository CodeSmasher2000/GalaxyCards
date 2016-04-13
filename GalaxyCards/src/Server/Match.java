package Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cards.HeroicSupport;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import game.Hero;
import guiPacket.Card;
import moves.PlayCard;

/**
 * This class contatins nessarsacry data and methods for storing data about
 * a match. It´s a thread since we 
 * @author patriklarsson
 *
 */
public class Match {
	private ClientHandler user1;
	private ClientHandler user2;
	private Player player1;
	private Player player2;
	
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
		player1 = new Player(user1);
		player2 = new Player(user2);
	}
	
	/**
	 * Takes a player that played a card and how the card was played and sends
	 * it to the other client
	 * @param player
	 * 		The player that made the move
	 * @param move
	 * 		The move that was played
	 */
	public void cardPlayed(Player player, PlayCard move) {
		// Send to the other player
		if (player.equals(player1)) {
			CommandMessage message = new CommandMessage(Commands.MATCH_MOVE,
					"Server", move);
			user2.writeMessage(message);
		} else if (player.equals(player2)) {
			CommandMessage message = new CommandMessage(Commands.MATCH_MOVE,
					"Server", move);
			user1.writeMessage(message);
		}
	}
	
	
	/**
	 * Contatins three lists representing the differnet lanes in a gameboard
	 * 
	 * @author patriklarsson
	 *
	 */
	private class Player implements Observer {
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
			clientHandler.addObserver(this);
			// TODO Ask the client for what hero it plays with
		}
		
		/**
		 * Adds a card to the defefensive lane or offensive lane
		 * 
		 * @param card
		 * 		The card object to add
		 * @param lane
		 * 		The lane where the card should be added
		 */
		public void playCard(PlayCard move) {
			PlayCard moveToSend;
			switch (move.getLane()) {
			case PLAYER_OFFENSIVE:
				offensiveLane.add(move.getCard());
				cardPlayed(this, move);
				break;
			case PLAYER_DEFENSIVE:
				defensiveLane.add(move.getCard());
				cardPlayed(this,move);
				break;
			default:
				break;
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			CommandMessage message = (CommandMessage)arg;
			if (message.getCommand() == Commands.MATCH_MOVE) {
				PlayCard move = (PlayCard)message.getData();
				playCard(move);
			}
		}
		
	}
	
	
	
}
