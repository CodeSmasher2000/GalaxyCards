package cards;

import java.io.Serializable;

import guiPacket.Card;

/**
 * 
 * @author 13120dde
 *
 */
public class HeroicSupport extends Card implements PlayCardsInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8195967465017595877L;
	private int defense;
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
	 * 
	 * @param name
	 *            : String
	 * @param rarity
	 *            : String
	 * @param imageName
	 *            : String
	 * @param hasAbility
	 *            : boolean
	 * 
	 * @param defense
	 *            : int
	 * @param PRICE
	 *            : int
	 */
	public HeroicSupport(String name, String rarity, String imageName, boolean hasAbility, int price, int defense) {
		NAME = name;
		RARITY = rarity;
		IMAGE_NAME = imageName;
		this.hasAbility = hasAbility;
		this.PRICE = price;
		this.defense = defense;
		setType(this);
		setName(NAME);
		setRarity(RARITY);
		setImage(IMAGE_NAME);
		hasAbility(this.hasAbility);
		setPrice(this.PRICE);
		super.setDefense(this.defense);
	}
	
	@Override
	public String toString() {
		return NAME + ", " + "HeroicSupport" + ", " + RARITY;
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

	/**
	 * Retruns the cost to play this card.
	 * 
	 * @return PRICE : int
	 */
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
	 * Sets defense by inrementing with amount passed in as argument. If a
	 * negative value is passed in, defense decreases.
	 */
	public void setDefense(int amount) {
		this.defense += amount;
	}


//	/**
//	 * Returns a String with the description of this card.
//	 * 
//	 * @return : String
//	 */
//	public String toString() {
//		return NAME + " - [HeroicSupport] Rarity: " + RARITY + ", image name: " + IMAGE_NAME + ", Defense: " + defense
//				+ ", Price: " + price + ", Has ability:" + hasAbility;
//	}

	/**
	 * Returns a String with the description of this card.
	 * 
	 * @return : String
	 */
	public String toString() {
		return NAME + " - [HeroicSupport] Rarity: " + RARITY + ", image name: " + IMAGE_NAME + "\nHas ability: "
				+ hasAbility + ", Price: " + PRICE + ", Defense: " + defense;
	
 

}
