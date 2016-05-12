package abilities;

import cards.Target;
import enumMessage.Lanes;

/**
 * This class represent a ability that untaps the target.
 * 
 * @author 13120dde
 *
 */
public class UntapTargetAbility extends Ability{

	private Target target;
	
	/**Instantiate this object by passing in a String description as argument.
	 * 
	 * @param description : String
	 */
	public UntapTargetAbility(String description){
		setDescription(description);
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
