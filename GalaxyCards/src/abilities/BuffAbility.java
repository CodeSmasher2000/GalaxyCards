package abilities;

/**
 * This ability buffs the target's defensive and attackvalues with the specified
 * ints passed in as argument to the constructor.
 * 
 * @author 13120dde
 *
 */
public class BuffAbility extends Ability {

	private int defValue, atkValue;

	/**
	 * Instantiate this object by passing in the given variables as argument.
	 * The string description represent the ability's description. The int
	 * values represent the amount to buff the target's defensive and attack
	 * attributes.
	 * 
	 * @param description
	 *            : String
	 * @param defValue
	 *            : int
	 * @param atkValue
	 *            : int
	 */
	public BuffAbility(String description, int defValue, int atkValue) {
		setDescription(description);
		setHasTarget(true);
		this.setDefValue(defValue);
		this.setAtkValue(atkValue);
	}

	/**
	 * Returns a int that represent the amount of defensive value to be buffed.
	 * 
	 * @return defensiveValue : int
	 */
	public int getDefValue() {
		return defValue;
	}

	/**Sets the defensive value with the int passed in as argument.
	 * 
	 * @param defValue : int
	 */
	public void setDefValue(int defValue) {
		this.defValue = defValue;
	}

	/**Returns a int that represent teh amount of attack value to be buffed.
	 * 
	 * @return atkValue : int
	 */
	public int getAtkValue() {
		return atkValue;
	}

	/**Sets the attack value with the int passed in as argument.
	 * 
	 * @param atkValue : int
	 */
	public void setAtkValue(int atkValue) {
		this.atkValue = atkValue;
	}
}
