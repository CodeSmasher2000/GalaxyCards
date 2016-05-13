package cards;

public interface PlayCardsInterface {
	
	/**
	 * Returns the name of the image used by this card. Image's are located in
	 * files/pictures directory.
	 * 
	 * @return IMAGE_NAME : String
	 */
	public String getImage();

	/**
	 * Returns the name of this card.
	 * 
	 */
	public String getName();

	/**
	 * Returns the rarity of this card.
	 * 
	 * @return RARITY : String
	 */
	public String getRarity();

	/**Display the ability description in the card's abilityPanel. MAX chars == 50!
	 * 
	 */
	public void setAbilityText(String description);

	
	/**
	 * Retruns the cost to play this card.
	 * 
	 * @return PRICE : int
	 */
	public int getPrice();

	/**
	 * Retruns the amount of defence this card has left.
	 * 
	 * @return defense : int
	 */
	public int getDefense();

	/**
	 * Sets defense by inrementing with amount passed in as argument. If a
	 * negative value is passed in, defense decreases.
	 */
	public void setDefense(int amount);


	/**
	 * Returns a String with the description of this card.
	 * 
	 * @return : String
	 */
	public String toString();

}
