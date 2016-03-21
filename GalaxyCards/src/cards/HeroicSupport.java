package cards;

import javax.swing.JFrame;

public class HeroicSupport extends Card {

	private int defense, price;
	private final String NAME;
	private final String RARITY;
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
	 * @param price
	 *            : int
	 */
	public HeroicSupport(String name, String rarity, String imageName, boolean hasAbility, int price, int defense) {
		NAME = name;
		RARITY = rarity;
		this.hasAbility = hasAbility;
		this.price = price;
		this.defense = defense;
		setType(this);
		setName(NAME);
		hasAbility(hasAbility);
		setRarity(RARITY);
		setPrice(this.price);
		setDefense(this.defense);
	}

}
