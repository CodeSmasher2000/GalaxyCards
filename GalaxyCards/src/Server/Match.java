package Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Target;
import cards.Tech;
import cards.Unit;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import enumMessage.Phase;
import exceptionsPacket.FullHandException;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.NotValidMove;
import exceptionsPacket.ResourcePlayedException;
import game.DefendingPhase;
import game.Hero;
import guiPacket.Card;
import move.Attack;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayTechCard;
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
	private int idCounter = 0;
	private int heroCounter = -2;

	// Instance Variables for what player is in defensive och attacking.
	private Player attacking;
	private Player defensive;
	private Player idle;

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

	/**
	 * Sets up the game to it´s inital state. Send messages to the two clients
	 * to draw 7 cards. And then decides who should be in what phase.
	 */
	public void initGamePhase() {
		// Both players should 7 draw cards
		for (int i = 0; i < 7; i++) {
			player1.drawCard();
			player2.drawCard();
		}

		// Decides who should start
		Random rand = new Random();
		int playerToStart = rand.nextInt(2);
		if (playerToStart == 0) {
			attacking = player1;
			user1.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, "server", Phase.ATTACKING));
			idle = player2;
			user2.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, "server", Phase.IDLE));
		} else {
			attacking = player2;
			user2.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, "server", Phase.ATTACKING));
			idle = player1;
			user1.writeMessage(new CommandMessage(Commands.MATCH_SET_PHASE, "server", Phase.IDLE));
		}
	}

	/**
	 * Swaps the players phases. The one what was in Attakcing will be defending
	 * and the other way.
	 */
	private void swapPhases() {
//		Player temp = attacking;
//		attacking = defensive;
//		defensive = temp;
//
//		// Taps and untaps the correct lanes
		attacking.tapDefensiveLane();
		attacking.untapOffensiveLane();
		defensive.tapOffensiveLane();
		defensive.untapDefensiveLane();
	}

	public void newRound() {
		defensive.setPhase(Phase.ATTACKING);
		// TODO : SWAP PHASES
		// swapPhases();

		// RESET HERO RESOURCES
		player1.hero.resetResources();
		player2.hero.resetResources();
		player1.updateHeroValues();
		player2.updateHeroValues();

		// EACH PLAYER DRAW 1 CARD
		player1.hero.DrawCard();
		player2.hero.DrawCard();

		// TODO : UNTAP CARDS
		swapPhases();
	}

	public void newPhase() {
		// TODO : Send message to client that a new phase begun
		// TODO : Untap cards in correct lane.
	}

	/**
	 * Is called when a commit attack message is recived from a client.
	 */
	public void commitAttackMove(Attack attack) {
		// SET THE IDLE PLAYER TO DEFENDINGs
		idle.setPhase(Phase.DEFENDING);
		// SET THE ATTACKING PLAYER TO IDLE
		attacking.setPhase(Phase.IDLE);

		// TODO : SEND ATTACK COMMITED TO THE OTHER DEFENING PLAYER
		defensive.defend(attack);
	}

	public void commitDefenseMove(Attack attack) {
		fight(attack);
		newRound();
	}

	public void fight(Attack attack) {
		for (int i = 0; i < attack.getLength(); i++) {
			boolean attackingCardFound = false;
			boolean defenderFound = false;
			int defender = attack.getDefender(i);
			int attacker = attack.getAttacker(i);
			Unit attackCard = null;
			Target target = null;

			// Tries to found the card that is attacking
			for (int j = 0; j < attacking.offensiveLane.size() & attackingCardFound; j++) {
				if (attacking.offensiveLane.get(j).getId() == attacker) {
					attackingCardFound = true;
					attackCard = (Unit) attacking.offensiveLane.get(j);
				}
			}

			// Tries to find the defedning card
			if (defender > 0) {
				for (int j = 0; j < defensive.defensiveLane.size() & defenderFound; j++) {
					if (defensive.defensiveLane.get(j).getId() == defender) {
						defenderFound = true;
						target = (Unit) defensive.defensiveLane.get(j);
					}
				}
				for (int j = 0; j < defensive.HeroicSupportLane.size() & defenderFound; j++) {
					if (defensive.HeroicSupportLane.get(j).getId() == defender) {
						defenderFound = true;
						target = defensive.HeroicSupportLane.get(j);
					}
				}
			} else if (defensive.hero.getId() == defender) {
				target = defensive.hero;
			}

			// DO THE FIGHT
			// TODO : CHECK IF TARGET IS DEAD
			target.damage(attackCard.getAttack());
			attackCard.damage(target.getDamage());
			defensive.updateTarget(target);
			idle.updateTarget(attackCard);
		}
	}

	/**
	 * This method sends the message to the other player that invokes this
	 * method
	 * 
	 * @param player
	 *            The player object the trades
	 * @param message
	 *            The message
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
	 * 
	 * @param player
	 *            A refernce to the player method
	 * @param message
	 *            The message to send
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
			} else if (object instanceof PlayTechCard) {
				PlayTechCard move = (PlayTechCard) object;
				player.playTechCard(move.getCard());
			} else if (object instanceof UpdateHeroValues) {
				UpdateHeroValues move = (UpdateHeroValues) object;
				player.updateHeroValues();
			} else if (message.getCommand() == Commands.MATCH_ATTACK_MOVE) {
				commitAttackMove((Attack)message.getData());
			} else if (message.getCommand() == Commands.MATCH_DEFEND_MOVE) {
				commitDefenseMove((Attack)message.getData());
			} 
		} else if (message.getCommand() == Commands.MATCH_DRAW_CARD) {
			player.drawCard();
		} else if (message.getCommand() == Commands.MATCH_NEW_ROUND) {
			newRound();
		}
	}

	/**
	 * This class stores the information about a players state in the match. The
	 * class have a list for HeroicSupportLane, DefensiveLane, and Offensive
	 * lane. Also uses a Hero object. And has a list for the scrapyard.
	 * Contatins methods for the different moves a player can make in a game.
	 *
	 * @author Patrik Larsson
	 *
	 */
	private class Player {
		private String name;
		private List<HeroicSupport> HeroicSupportLane = new LinkedList<HeroicSupport>();
		private List<Card> defensiveLane = new LinkedList<Card>();
		private List<Card> offensiveLane = new LinkedList<Card>();
		private Hero hero = new Hero(heroCounter++);
		private List<Card> hand = new LinkedList<Card>();
		private List<Card> scrapYard = new ArrayList<Card>();
		private Phase phase;

		/**
		 * Gives a player a name from the clientHandler
		 * 
		 * @param clientHandler
		 *            The clienthandler to get the name from
		 */
		public Player(ClientHandler clientHandler) {
			this.name = clientHandler.getActiveUser();
		}

		/**
		 * Asks the player for a defeing move
		 * 
		 * @param attack
		 *            The attack object to modifys
		 */
		public void defend(Attack attack) {
			// Send the move to the client
			sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_DEFEND_MOVE, "Server", attack));
		}

		/**
		 * Sets a player to a phase and sends a message to update the client
		 * 
		 * @param phase
		 *            The phase to set.
		 */
		public void setPhase(Phase phase) {
			if (phase == Phase.ATTACKING) {
				attacking = this;
			} else if (phase == Phase.DEFENDING) {
				defensive = this;
			} else if (phase == Phase.IDLE) {
				idle = this;
			}
			sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_SET_PHASE, "Server", phase));
		}

		/**
		 * Tries to play a heroicsupport card and if there allready is two
		 * heroic support cards on the board for the player or if the player
		 * dosént have enought resources a error message is sent back otherwise
		 * a message with the move is sent back to the client and to the other
		 * player aswell.
		 * 
		 * @param move
		 *            The move the client wants to make
		 */
		public void playHeroicSupport(PlayHeroicSupportCard move) {
			try {
				hero.useResource(move.getCard().getPrice());
				if (HeroicSupportLane.size() >= 2) {
					throw new NotValidMove("You allready have two heroic support cards");
				}

				HeroicSupportLane.add(move.getCard());
				hand.remove(move.getCard());
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_PLACE_CARD, this.name, move));
				updateHeroValues();
			} catch (InsufficientResourcesException | NotValidMove e) {
				CommandMessage commandMessage = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "Server", e);
				sendMessageToPlayer(this, commandMessage);
				e.printStackTrace();
			}

		}

		/**
		 * Is invoked when a player wants to play a Unit card. If to move is
		 * succeded the move is sent to both clients that is connected to the
		 * match. If it´s not the client that iniated the move gets a error
		 * message back.
		 * 
		 * @param move
		 *            A object containg both the card and the lane it should be
		 *            placed int.
		 */
		public void playUnitCard(PlayUnitCard move) {
			try {
				hero.useResource(move.getCard().getPrice());
				// Updates the Gui with the new values
				updateHeroValues();

				// Do The Move server side
				if (move.getLane() == Lanes.PLAYER_OFFENSIVE) {
					if (offensiveLane.size() >= 6) {
						throw new NotValidMove("Offensive lane is full");
					}
					this.offensiveLane.add(move.getCard());
				} else if (move.getLane() == Lanes.PLAYER_DEFENSIVE) {
					if (defensiveLane.size() >= 6) {
						throw new NotValidMove("Defensive lane is full");
					}
					this.defensiveLane.add(move.getCard());
				}
				hand.remove(move.getCard());

				// Send to the client that made the move
				CommandMessage message = new CommandMessage(Commands.MATCH_PLACE_CARD, "Server", move);
				sendMessageToPlayer(this, message);

				// Send to the other client
				message = new CommandMessage(Commands.MATCH_PLAYCARD, "Server", move);
				sendMessageToOtherPlayer(this, message);
			} catch (InsufficientResourcesException | NotValidMove e) {
				// Sends error message back to the client
				CommandMessage message = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "Server", e);
				sendMessageToPlayer(this, message);
			}

		}

		/**
		 * Tries to add a resource point to the hero. If sucesseds it uppdates
		 * the heroValues on both clients and removes the card from the hand
		 * 
		 * @param move
		 */
		public void playResourceCard(PlayResourceCard move) {
			ResourceCard card = move.getCard();
			try {
				hero.addResource();
				move.setUpdateHeroValues(new UpdateHeroValues(hero.getLife(), hero.getEnergyShield(),
						hero.getCurrentResources(), hero.getMaxResource()));
				updateHeroValues();
				hand.remove(card);
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_PLACE_CARD, this.name, move));
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, this.name, move));

				scrapYard.add(move.getCard());
			} catch (ResourcePlayedException e) {
				CommandMessage error = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "server", e);
				sendMessageToPlayer(this, error);
			}

		}

		public void playTechCard(Tech card) {

			try {
				hero.useResource(card.getPrice());
				addCardToScrapYard(card);
				hand.remove(card);
				// Send to player who initatied move
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_PLACE_CARD, "Server", card));
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_PLAYCARD, "Server", card));
			} catch (InsufficientResourcesException e) {
				CommandMessage error = new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "Server", e);
				sendMessageToPlayer(this, error);
			}
		}

		/**
		 * Anropas när hjätarnas värde uppdateras
		 * 
		 * @param move
		 */
		public void updateHeroValues() {
			UpdateHeroValues move = new UpdateHeroValues(hero.getLife(), hero.getEnergyShield(),
					hero.getCurrentResources(), hero.getMaxResource());
			sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_UPDATE_OPPONENT_HERO, this.name, move));
			sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_UPDATE_FRIENDLY_HERO, this.name, move));

		}

		/**
		 * Tries to draw a card and add it to the hand. IF the hand is full
		 * discards one random card and adds the new one.
		 */
		public void drawCard() {
			// Draw the card
			Card card = hero.DrawCard();
			card.setId(++idCounter);

			// Check if the hand is full
			if (hand.size() < 8) {
				hand.add(card);
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_FRIENDLY_DRAW_CARD, "Server", card));
				sendMessageToOtherPlayer(this, new CommandMessage(Commands.MATCH_OPPONENT_DRAW_CARD, "Server", card));

			} else {
				discardRandomCard();
				FullHandException e = new FullHandException("Hand is full can´t draw new card");
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_NOT_VALID_MOVE, "server", e));
				// Should still add the new card to the hand
				sendMessageToPlayer(this, new CommandMessage(Commands.MATCH_FRIENDLY_DRAW_CARD, "Server", card));
			}
		}

		private void discardRandomCard() {
			// Calculates the card to discard
			Random rand = new Random();
			int i = rand.nextInt(hand.size());
			Card card = removeCardFromHand(hand.get(i));

			// Send messages to the clients
			CommandMessage message = new CommandMessage(Commands.MATCH_REMOVE_CARD, "server", card);
			sendMessageToPlayer(this, message);
			message = new CommandMessage(Commands.MATCH_ADD_TO_OPPONET_SCRAPYARD, "server", card);
			sendMessageToOtherPlayer(this, message);
		}

		/**
		 * Utility method that removes the card from hand and adds to
		 * playerScrapyard
		 */
		private Card removeCardFromHand(Card cardToRemove) {
			for (int i = 0; i < hand.size(); i++) {
				if (cardToRemove.compareTo(hand.get(i)) == 0) {
					hand.remove(i);
					addCardToScrapYard(cardToRemove);
					// TODO SEND MESSAGES TO CLIENT THAT A CARD SHOULD BE
					// REMOVED
					return cardToRemove;
				}
			}
			System.out.println("RemoveCardFromHand: Something went wrong");
			return null;
		}

		/**
		 * Adds a card to the scrapyard. If there is more than 5 cards in the
		 * scrapyard it removes the first.
		 * 
		 * @param cardToAdd
		 */
		private void addCardToScrapYard(Card cardToAdd) {
			if (scrapYard.size() == 5) {
				scrapYard.remove(0);
			}
			scrapYard.add(cardToAdd);
			// TODO Send message to clients to update GUI
		}

		/**
		 * Untaps a specifc card and send messages to the clients to update
		 * their guis
		 * 
		 * @param card
		 *            The card to untap
		 */
		public void untapCard(Card card) {
			boolean cardFound = false;
			if (card instanceof HeroicSupport) {
				for (int i = 0; i < HeroicSupportLane.size() & !cardFound; i++) {
					if (HeroicSupportLane.get(i).compareTo(card) == 0) {
						((HeroicSupport) card).untap();
						cardFound = true;
						// TODO Send message to clients that this card is
						// untapped
					}
				}

			} else if (card instanceof Unit) {
				// TODO Check if the card is in defensive lane, or offensivelane
				for (int i = 0; i < offensiveLane.size() & !cardFound; i++) {
					if (card.compareTo(offensiveLane.get(i)) == 0) {
						((Unit) card).tap();
						cardFound = true;
						// TODO Send message to the clients that this card is
						// untapped
					}
				}

				for (int i = 0; i < defensiveLane.size() & !cardFound; i++) {
					if (defensiveLane.get(i).compareTo(card) == 0) {
						((Unit) card).tap();
						cardFound = true;
						// TODO Send message eto the clients that this card is
						// untapped
					}
				}
			}
		}

		/**
		 * Sets a specific card to tapped and send messages to the clients to
		 * update thier guis
		 * 
		 * @param card
		 *            The card to tap
		 */
		public void tapCard(Card card) {
			boolean cardFound = false;
			if (card instanceof HeroicSupport) {
				for (int i = 0; i < HeroicSupportLane.size() & !cardFound; i++) {
					if (HeroicSupportLane.get(i).compareTo(card) == 0) {
						((HeroicSupport) card).tap();
						// TODO Send message to clients that this card is tapped
					}
				}

			} else if (card instanceof Unit) {
				for (int i = 0; i < offensiveLane.size() & !cardFound; i++) {
					if (card.compareTo(offensiveLane.get(i)) == 0) {
						((Unit) card).tap();
						cardFound = true;
					}
				}

				for (int i = 0; i < defensiveLane.size() & !cardFound; i++) {
					if (defensiveLane.get(i).compareTo(card) == 0) {
						((Unit) card).tap();
						cardFound = true;
					}
				}
			}
		}

		/**
		 * Taps the units in offensiveLane
		 */
		public void tapOffensiveLane() {
			for (int i = 0; i < offensiveLane.size(); i++) {
				TapCardInOffensiveLane(i);
			}
		}

		/**
		 * Untaps the units in OffensiveLane
		 */
		public void untapOffensiveLane() {
			for (int i = 0; i < offensiveLane.size(); i++) {
				untapCardInOffensiveLane(i);
			}
		}

		/**
		 * Untaps the units in defensive lane
		 */
		public void untapDefensiveLane() {
			for (int i = 0; i < defensiveLane.size(); i++) {
				untapCardInDefensiveLane(i);
			}
		}

		/**
		 * Taps the units in defensive lane.
		 */
		public void tapDefensiveLane() {
			for (int i = 0; i < defensiveLane.size(); i++) {
				TapCardInDefensiveLane(i);
			}
		}

		public void untapCardInDefensiveLane(int index) {
			((Unit) defensiveLane.get(index)).untap();
			// TODO : SEND MESSAGE TO CLIENT THAT A CARD SHOULD BE UNTAPPED
		}

		public void untapCardInOffensiveLane(int index) {
			((Unit) offensiveLane.get(index)).untap();
			// TODO : SEND MESSAGE TO CLIENT THAT A CARD SHOULD BE UNTAPPED
		}

		public void TapCardInOffensiveLane(int index) {
			((Unit) offensiveLane.get(index)).tap();
			// TODO : SEND MESSAGE TO CLIENT THAT A CARD SHOULD BE TAPPED
		}

		public void TapCardInDefensiveLane(int index) {
			((Unit) defensiveLane.get(index)).untap();
			// TODO : SEND MESSAGE TO CLIENT THAT A CARD SHOULD BE TAPPED
		}

		public void updateTarget(Target target) {
			if (target instanceof HeroicSupport || target instanceof Unit) {
				CommandMessage message = new CommandMessage(Commands.MATCH_UPDATECARD, "Server", target);
				sendMessageToPlayer(player1, message);
				sendMessageToPlayer(player2, message);
				if (target.isDead()) {
					HeroicSupportLane.remove(target);
				}
			} else if(target instanceof Unit) {
				CommandMessage message = new CommandMessage(Commands.MATCH_UPDATECARD, "Server", target);
				sendMessageToPlayer(player1, message);
				sendMessageToPlayer(player2, message);
				if (target.isDead()) {
					if (defensiveLane.contains(target)) {
						defensiveLane.remove(target);
					} else if (defensiveLane.contains(target)) {
						defensiveLane.remove(target);
					} 
				}
			} else if (target instanceof Hero) {
				if (target.equals(player1.hero)) {
					player1.updateHeroValues();
				} else if (target.equals(player2)) {
					player2.updateHeroValues();
				}
			}
		}
	}

}
