package abilities;

/**
 * Class that enables the player to draw a card from his deck whenever this
 * ability is played.
 * 
 * @author 13120dde
 *
 */
public class DrawCardAbility extends Ability {

	/**
	 * Instantiate the object by passing in a String description of the ability
	 * as argument.
	 * 
	 * @param description
	 *            : String
	 */
	public DrawCardAbility(String description) {
		setDescription(description);
	}
}
