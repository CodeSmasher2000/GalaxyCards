package cards;

import java.io.Serializable;

import guiPacket.Card;

/**
 * 
 * @author 13120dde
 *
 */
public class Unit extends Card implements PlayCardsInterface, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5013183003151976993L;
	private int attack, defense;
	private final int PRICE;
	private final String NAME, RARITY, IMAGE_NAME;
	private boolean hasAbility;

	/**
	 * Constructor instantiates this card with given arguments to configure its
	 * visuals by calling upon it's parent's methods. The rarity argument must
	 * be "common", "rare" or "legendary" (not case sensitive and no
	 * whitespace). The imageName argument must match the image's name and the
	 * imagefile must be in files/pictures directory.
	 * 
	 * @param name
	 *            : String
	 * @param rarity
	 *            : String
	 * @param imageName
	 *            : String
	 * @param hasAbility
	 *            : boolean
	 * @param attack
	 *            : int
	 * @param defense
	 *            : int
	 * @param price
	 *            : int
	 */
	public Unit(String name, String rarity, String imageName, boolean hasAbility, int attack, int defense, int price) {
		NAME = name;
		RARITY = rarity;
		IMAGE_NAME = imageName;
		this.hasAbility = hasAbility;
		this.attack = attack;
		this.defense = defense;
		this.PRICE = price;
		setType(this);
		setName(NAME);
		setRarity(RARITY);
		setImage(IMAGE_NAME);
		hasAbility(this.hasAbility);
		setPrice(PRICE);
		super.setAttack(this.attack);
		super.setDefense(this.defense);

	}

	/**
	 * Returns the name of the image used by this card. Image's are located in
	 * files/pictures directory.
	 * 
	 * @return IMAGE_NAME : String
	 */
	public String getImage() {
		return IMAGE_NAME;
	}

	/**
	 * Returns the name of this card.
	 * 
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Returns the rarity of this card.
	 * 
	 * @return RARITY : String
	 */
	public String getRarity() {
		return RARITY;
	}

	/**
	 * Return true if this card has ability, else false.
	 * 
	 * @return :boolean
	 */
	public boolean hasAbility() {
		return hasAbility;
	}
	public void setAbilityText(String description){
		super.setAbilityText(description);
	}

	public int getPrice() {
		return PRICE;
	}

	/**
	 * Retruns the amount of defence this card has left.
	 * 
	 * @return defense : int
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Sets defense by incrementing with amount passed in as argument. If a
	 * negative value is passed in, defense decreases.
	 */
	public void setDefense(int amount) {
		this.defense += amount;
	}

	/**
	 * Sets attack by incrementing with amount passed in as argument. If a
	 * negative value is passed in, attack decreases. Attack can not be smaller
	 * than 0.
	 */
	public void setAttack(int amount) {
		this.attack += amount;
		if (this.attack < 0) {
			this.attack = 0;
		}
	}

	/**
	 * Returns the amount of attack this card has.
	 * 
	 * @return attack : int
	 */
	public int getAttack() {
		return attack;
	}
	
	public String toString() {
		return NAME + " - [HeroicSupport] Rarity: " + RARITY + ", image name: " + IMAGE_NAME + "\nHas ability: "
				+ hasAbility + ", Price: " + PRICE + ", Defense: " + defense;
	}


}
