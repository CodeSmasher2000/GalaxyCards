package Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import cards.HeroicSupport;
import cards.ResourceCard;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import enumMessage.Phase;
import exceptionsPacket.FullHandException;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import game.Hero;
import guiPacket.Card;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayUnitCard;
import move.UpdateHeroValues;

/**
 * This class contatins nessarsacry data and methods for storing data about a
 * match.
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

		// Both players should 7 draw cards
		for (int i = 0; i < 7; i++) {
			player1.drawCard();
			player2.drawCard();
		}

		// Avgör vem som ska börja
		Random rand = new Random();
		int playerToStart = rand.nextInt(2);
		if (playerToStart == 0) {
			attacking = player1;
			user1.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, 
					"server", Phase.ATTACKING));
			defensive = player2;
			user2.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE,
					"server", Phase.IDLE));
		} else {
			attacking = player2;
			user2.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, "server",
					Phase.ATTACKING));
			defensive = player1;
			user1.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE,
					"server", Phase.IDLE));
		}
	}
	
	public void newRound() {
		// TODO: SWAP PHASES
		
		// RESET HERO RESOURCES
		player1.hero.resetResources();
		player2.hero.resetResources();
		
		// EACH PLAYER DRAW 1 CARD
		player1.hero.DrawCard();
		player2.hero.DrawCard();
		
		// TODO: UNTAP CARDS
		
	}
	
	/**
	 * This method sends the message to the other player that invokes this method
	 * 
	 * @param player
	 * 		The player object the trades
	 * @param message
	 * 		The message
	 */
	public void sendMessageToOtherPlayer(Player player, CommandMessage message) {
		if (player.equals(player1)) {
			user2.writeMessage(message);
		} else if (player.equals(player2)) {
			user1.writeMessage(message);
		}
	}
	
	/**
	 * This method sends the message to the player that invokes this method
	 * @param player
	 * 		A refernce to the player method
	 * @param message
	 * 		The message to send
	 */
	public void sendMessageToPlayer(Player player, CommandMessage message) {
		if (player.equals(player1)) {
			user1.writeMessage(message);
		} else if (player.equals(player2)) {
			user2.writeMessage(message);
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
			} else if (object instanceof UpdateHeroValues) {
				UpdateHeroValues move = (UpdateHeroValues) object;
				player.updateHeroValues(move);
			}
		} else if (message.getCommand() == Commands.MATCH_DRAW_CARD) {
			player.drawCard();
		} else if(message.getCommand() == Commands.MATCH_ATTACK_MOVE) {
			sendMessageToOtherPlayer(player, message);
		} else if(message.getCommand() == Commands.MATCH_DEFEND_MOVE) {
			sendMessageToOtherPlayer(player, message);
		} else if(message.getCommand() == Commands.MATCH_NEW_ROUND) {
			newRound();
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
		private Hero hero = new Hero();
		private List<Card> hand = new LinkedList<Card>();
		private List<Card> scrapYard = new ArrayList<Card>();
		private ClientHandler clientHandler;

		public Player(ClientHandler clientHandler) {
			this.clientHandler = clientHandler;
			this.name = clientHandler.getActiveUser();
			// TODO Ask the client for what hero it plays with
		}

		public void playHeroicSupport(PlayHeroicSupportCard move) {
			// TODO: Check if move is valid
			try {
				hero.useResource(move.getCard().getPrice());
				HeroicSupportLane.add(move.getCard());
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_PLACE_CARD, this.name, move));
			} catch (InsufficientResourcesException e) {
				CommandMessage commandMessage = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE,
						"Server", e);
				sendMessageToPlayer(this, commandMessage);
				e.printStackTrace();
			}
	
		}

		public void playUnitCard(PlayUnitCard move) {
			// TODO: Check if move is valid.
			try {
				hero.useResource(move.getCard().getPrice());
				// Send to the client that made the move
				CommandMessage message = new CommandMessage(Commands.MATCH_PLACE_CARD,
						"Server", move);
				sendMessageToPlayer(this, message);
				
				// Send to the other client
				message = new CommandMessage(Commands.MATCH_PLAYCARD, "Server",
						move);
			} catch (InsufficientResourcesException e) {
				// Send Response to client that made move
				CommandMessage message = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "Server",
						e);
			}
			
		}

		public void playResourceCard(PlayResourceCard move) {
			ResourceCard card = move.getCard();
			try {
				hero.addResource();
				move.setUpdateHeroValues(new UpdateHeroValues(hero.getLife(),
						hero.getEnergyShield(), hero.getCurrentResources(),
						hero.getMaxResource()));
				hand.remove(card);
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_PLACE_CARD, this.name, move) );
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
				
				scrapYard.add(move.getCard());
			} catch (ResourcePlayedException e) {
				CommandMessage error = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "server",e);
				sendMessageToPlayer(this, error);
			}

		}

		public void updateHeroValues(UpdateHeroValues move) {
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_UPDATE_HERO, this.name, move));
		}

		public void drawCard() {
			// Check if the hand is full
			if (hand.size() < 8) {
				Card card = hero.DrawCard();
				hand.add(card);
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_FRIENDLY_DRAW_CARD, "Server",
						card));
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_OPPONENT_DRAW_CARD, "Server",
						card));
				
			} else {
				// TODO Add to the scrapyard.
				FullHandException e = new FullHandException("Hand is full can´t draw new card");
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_NOT_VALID_MOVE,
						"server", e));
			}
		}
	}

}
