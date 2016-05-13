package abilities;

import cards.Target;

/**
 * This class represent a single target ability that needs a target. This class
 * can either be used to deal damage or heal the target depending on if the
 * value is a positive or negative integer.
 * 
 * @author 13120dde
 *
 */
public class SingleTargetAbility extends Ability {

	private Target target;
	private int value;

	/**
	 * Instantiate this object with two values passed in as argument. A int that
	 * represent the amount of damage/heal and a String description of the
	 * ability.
	 * 
	 * @param value
	 *            : int
	 * @param description
	 *            : String
	 */
	public SingleTargetAbility(int value, String description) {
		setValue(value);
		setDescription(description);
		setHasTarget(true);
	}

	/**Returns the target of this ability.
	 * 
	 * @return target : Target
	 */
	public Target getTarget() {
		return target;
	}

	/**Sets the target for this ability.
	 * 
	 * @param target : Target
	 */
	public void setTarget(Target target) {
		this.target = target;
	}

	/**Returns the weight of this ability's effect.
	 * 
	 * @return value : int
	 */
	public int getValue() {
		return value;
	}

	/**Set the weight of this abbility's effect.
	 * 
	 * @param value : int
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
