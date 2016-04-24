package move;

import java.io.Serializable;

import cards.Unit;
import enumMessage.Lanes;
import guiPacket.Card;

/**
 * This class boxes in information about when a UnitCard is played. Contains
 * getters and setters for the card and and lane of the card
 * 
 * @author Patrik
 *
 */
public class PlayUnitCard implements Serializable {
	// Variables for storing data about the card
	private String imageName;
	private String name;
	private int defense;
	private int attack;
	private int price;
	private String abilityText;
	private String rarity;
	
	// Variables for storing the data about the play
	private static final long serialVersionUID = 1L;
	private Lanes lane;
	
	/**
	 * The method returns a new card recreated from the data.
	 * @return
	 * 		A new card object
	 */
	public Unit getCard() {
		boolean hasAbility = false;
		// Kontrollerar ifall kortet har en ability.
		if (abilityText != null) {
			hasAbility = true;
		}
		return new Unit(name, rarity, imageName, hasAbility, attack, defense, price);
	}

	public Lanes getLane() {
		return lane;
	}

	public void setLane(Lanes lane) {
		this.lane = lane;
	}

	public PlayUnitCard(Unit card, Lanes lane) {
		this.imageName = card.getImage();
		this.defense = card.getDefense();
		this.attack = card.getAttack();
		this.price = card.getPrice();
		this.abilityText = card.getAbilityText();
		this.rarity = card.getRarity();
	}

}
