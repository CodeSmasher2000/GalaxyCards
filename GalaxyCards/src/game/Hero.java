package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import cards.Deck;
import cards.Target;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.InsufficientShieldException;
import exceptionsPacket.ResourcePlayedException;
import exceptionsPacket.InsufficientResourcesException;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;

/**
 * This class represents the player's hero and is responsible for a logical
 * representation of the hero and his attributes. This class is also responsible
 * for altering the hero's attributes and holds a deck object from which the
 * player draws Cards.
 * 
 * @author 13120dde
 *
 */

public class Hero implements Serializable, Target {

	/**
	 * 
	 */
	private static final long serialVersionUID = -704227521994333558L;

	private Deck deck;
	private String heroName = "Supreme Commander";
	private int life, energyShield, maxResource, currentResource, incrementalDamage = 0;
	private boolean resourceCardPlayedThisRound = false;
	private int id;

	public Hero(int id) {
		life = 20;
		energyShield = 10;
		maxResource = 0;
		currentResource = 0;
		this.deck = loadDeck();
		deck.shuffle();
		this.id = id;
	}

	public Hero(String heroName) {
		this.heroName = heroName;
		life = 20;
		energyShield = 10;
		maxResource = 0;
		currentResource = 0;
		loadDeck();
	}

	public Deck loadDeck() {

		File file = new File("files/decks/Ability.dat");

		try (FileInputStream fin = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fin)) {
			return (Deck) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	/**
	 * Attempts to draw a card from the deck and return the card object. If deck
	 * is empty deals incremental damage to the player's hero.
	 * 
	 * @return
	 */
	public Card DrawCard() {
		try {
			return deck.drawCard();
		} catch (EmptyDeckException e) {
			dealDamage(incrementalDamage++);
			InfoPanelGUI.append("For every round your hero takes incremental damage: " + incrementalDamage);
			return null;
		}
	}

	/**
	 * Deal damage to this hero. The damage is dealt first to the hero's energy
	 * shield. If the shield is depleted the damage is dealt to the hero's life.
	 * If the damage is higher than the amount of energy shield, any excessive
	 * damage won't go trough to the hero's life.
	 * 
	 * @param amount
	 *            : int
	 */
	public void dealDamage(int amount) {
//		if (energyShield > 0) {
//			energyShield -= amount;
//
//		} else if(amount <= 0) {
//			energyShield = Math.min(10, (energyShield - amount));
//		}
//		else {
//			energyShield = 0;
//			life -= amount;
//		}
		
		if (amount < 0) {
			// The maximun shield of the hero should be 10
			addShield(-amount);
		} else if(energyShield < 0) {
			energyShield = Math.max(0, energyShield - amount);
		} else {
			life -= amount;
		}

	}

	/**
	 * Add x amount of energy shield to this hero. The shield's max amount is 10
	 * and if amount is higher than 10 the energyShield attribute will be set to
	 * 10.
	 * 
	 * @param amount
	 */
	public void addShield(int amount) {
		energyShield = energyShield + amount;
		if (energyShield > 10) {
			energyShield = 10;
		}
	}

	/**
	 * Attempts to remove x amount of energy shield from the hero. If x is
	 * higher than the amount of shield then throws exception.
	 * 
	 * @param amount
	 *            : int
	 * @return
	 * @throws InsufficientShieldException
	 */
	public void removeShield(int amount) throws InsufficientShieldException {
		if (energyShield == 0 || energyShield < amount) {
			throw new InsufficientShieldException("Insufficient Shield");
		} else {
			energyShield -= amount;
		}

	}

	/**
	 * Returns the hero's current life value.
	 * 
	 * @return life : int
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Returns the hero's current energyShield value.
	 * 
	 * @return energyShield : int
	 */
	public int getEnergyShield() {
		return energyShield;
	}

	public int getCurrentResources() {
		return currentResource;
	}

	public int getMaxResource() {
		return maxResource;
	}

	/**
	 * Attempts to add 1 resource to the hero's currentResource and maxResource
	 * values. Throws exception if resourceCard is played this turn.
	 * 
	 * @throws ResourcePlayedException
	 */
	public boolean addResource() throws ResourcePlayedException {
		boolean addResourceOK = false;
		if (!resourceCardPlayedThisRound) {
			maxResource++;
			currentResource++;
			resourceCardPlayedThisRound = true;
			addResourceOK = true;
		} else {
			throw new ResourcePlayedException("You can only play one resource card each turn");
		}
		return addResourceOK;

	}

	/**
	 * Attempts to use the hero's current resource to play a card from hand.
	 * Throws exception if insufficient resources avaible.
	 * 
	 * @param amount
	 *            : int
	 * @throws InsufficientResourcesException
	 */
	public boolean useResource(int amount) throws InsufficientResourcesException {
		boolean useResourceOK = false;
		if (amount > currentResource) {
			throw new InsufficientResourcesException("Not enough resources");

		} else {
			currentResource -= amount;
			useResourceOK = true;
		}
		return useResourceOK;
	}

	/**
	 * When a new round starts call this method to reset hero's currentResources
	 * and enable him to play a resource card from hand.
	 */
	public void resetResources() {
		currentResource = maxResource;
		resourceCardPlayedThisRound = false;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getHeroName() {
		return heroName;
	}

	public String toString() {
		return "[ " + heroName + " ] Life: " + life + "Energyshield: " + energyShield + " Resources: " + currentResource
				+ " / " + maxResource;
	}

	@Override
	public void damage(int amt) {
		dealDamage(amt);
	}

	@Override
	public int getId() {
		return this.id;
	}

	public int setId(int id) {
		return this.id = id;
	}

	@Override
	public int getDamage() {
		return 0;
	}

	@Override
	public int getDefense() {
		return life;

	}

	@Override
	public boolean isDead() {
		if (life <= 0) {
			return true;
		}
		return false;
	}
}
