package cards;

import java.io.Serializable;

public class Tech extends Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1803659048435357727L;
	private final String NAME, RARITY, IMAGE_NAME;
	private final int PRICE;

	public Tech(String name, String rarity, String imageName, int price){
		NAME=name;
		RARITY=rarity;
		IMAGE_NAME= imageName;
		PRICE = price;
		setType(this);
		setName(NAME);
		setRarity(RARITY);
		setImage(IMAGE_NAME);
		hasAbility(false);
		setPrice(PRICE);
		
	}

}
