package abilities;

/**
 * This abstract class is the superclass of all ability classes. One thing in
 * common for all abilities is the description String value. Ability classes
 * hold data about themselves and eventual targets. The logic behind the ability
 * is applied in the Match class.
 * 
 * @author 13120dde
 *
 */

public abstract class Ability {

	private String description;

	/**
	 * Returns a String that represent the description of the ability.
	 * 
	 * @return description : String
	 */
	public String toString() {
		return description;
	}

	/**
	 * Set the description for the ability. the argument passed in is String.
	 * 
	 * @param txt
	 *            : String
	 */
	public void setDescription(String txt) {
		description = txt;
	}
}
