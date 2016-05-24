package abilities;

/**
 * Class that enables the player to draw a card from his deck whenever this
 * ability is played.
 * 
 * @author 13120dde
 *
 */
public class DrawCardAbility extends Ability {

	
	private int amount;
	
	/**
	 * Instantiate the object by passing in a String description of the ability
	 * as argument.
	 * 
	 * @param description
	 *            : String
	 */
	public DrawCardAbility(String description, int amount) {
		setDescription(description);
		setHasTarget(false);
		setAmount(amount);
	}

	/**Return the amount of cards to be drawn.
	 * 
	 * @return amount : int
	 */
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
