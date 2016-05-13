package abilities;

import cards.Target;

/**This class represent a ability that will tap the target card.
 * 
 * @author 13120dde
 *
 */

public class TapTargetAbility extends Ability {

	private Target target;
	
	/**Instantiate this object by passing in a String description as argument.
	 * 
	 * 
	 * @param description : String
	 */
	public TapTargetAbility(String description){
		setDescription(description);
		setHasTarget(true);
	}

	/**Returns the target for this ability.
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
	
}
