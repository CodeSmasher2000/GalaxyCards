package Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import cards.HeroicSupport;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import game.Hero;
import guiPacket.Card;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayUnitCard;
import move.UpdateHeroValues;

/**
 * This class contatins nessarsacry data and methods for storing data about a
 * match. It´s a thread since we
 * 
 * @author patriklarsson
 *
 */
public class Match implements Observer {
	private ClientHandler user1;
	private ClientHandler user2;
	private Player player1;
	private Player player2;

	// Instance Variables for what player is in defensive och attacking.
	private Player attacking;
	private Player defensive;

	/**
	 * The constructor sets up a Match object with two clientHandlers that are
	 * playing the match.
	 * 
	 * @param user1
	 *            A Reference to a clienthandler object.
	 * @param user2
	 *            A Reference to a clienhandler object.
	 */
	public Match(ClientHandler user1, ClientHandler user2) {
		this.user1 = user1;
		this.user2 = user2;
		this.user1.addObserver(this);
		this.user2.addObserver(this);
		System.out.println("Server: Match Started");
		player1 = new Player(user1);
		player2 = new Player(user2);
		initGamePhase();
	}

	public void initGamePhase() {
		// Vänta på meddelande från båda klienter att de har fått upp fönstret

		// Both players should draw cards
		CommandMessage message = new CommandMessage(Commands.MATCH_INIT_GAME, "Server");
		user1.writeMessage(message);
		user2.writeMessage(message);

		// Avgör vem som ska börja
		Random rand = new Random();
		int playerToStart = rand.nextInt(2);
		if (playerToStart == 0) {
			attacking = player1;
			defensive = player2;
		} else {
			attacking = player2;
			defensive = player1;
		}

		// Skicka till klient för att sätta rätt fas
	}

	public void sendMessageToOtherPlayer(Player player, CommandMessage message) {
		if (player.equals(player1)) {
			user2.writeMessage(message);
		} else if (player.equals(player2)) {
			user1.writeMessage(message);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// Kontrollerar vilken spelare som har gjort draget
		Player player = null;
		if (o.equals(user1)) {
			player = player1;
		} else if (o.equals(user2)) {
			player = player2;
		}

		// Läser av meddelanded och hämtar data objektet
		CommandMessage message;
		Object object;
		message = (CommandMessage) arg;
		// Eftersom alla messages inte innehåller någon data.
		if (message.getData() != null) {
			object = message.getData();
			if (object instanceof PlayUnitCard) {
				PlayUnitCard move = (PlayUnitCard) object;
				player.playUnitCard(move);
			} else if (object instanceof PlayHeroicSupportCard) {
				PlayHeroicSupportCard move = (PlayHeroicSupportCard) object;
				player.playHeroicSupport(move);
			} else if (object instanceof PlayResourceCard) {
				PlayResourceCard move = (PlayResourceCard) object;
				player.playResourceCard(move);
			}
		} else if (message.getCommand() == Commands.MATCH_DRAW_CARD) {
			player.drawCard();
		}else if (object instanceof UpdateHeroValues){
			UpdateHeroValues move = (UpdateHeroValues)object;
			player.updateHeroValues(move);
		}
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
		private List<Card> scrapYard = new ArrayList<Card>();
		private ClientHandler clientHandler;

		public Player(ClientHandler clientHandler) {
			this.clientHandler = clientHandler;
			this.name = clientHandler.getActiveUser();
			// TODO Ask the client for what hero it plays with
		}

		public void playHeroicSupport(PlayHeroicSupportCard move) {
			HeroicSupportLane.add(move.getCard());
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
		}

		public void playUnitCard(PlayUnitCard move) {
			if (move.getLane() == Lanes.PLAYER_DEFENSIVE) {
				defensiveLane.add(move.getCard());
			} else if (move.getLane() == Lanes.PLAYER_OFFENSIVE) {
				offensiveLane.add(move.getCard());
			}
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
		}

		public void playResourceCard(PlayResourceCard move) {
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
			scrapYard.add(move.getCard());
		}
		
		public void updateHeroValues(UpdateHeroValues move){
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_UPDATE_HERO, this.name, move));
		}

		public void drawCard() {
			CommandMessage message = new CommandMessage(Commands.MATCH_DRAW_CARD, this.name);
			sendMessageToOtherPlayer(this, message);
		}
	}
	
	
	
	
	
}
