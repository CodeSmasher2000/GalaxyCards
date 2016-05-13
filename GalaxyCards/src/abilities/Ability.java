package abilities;

import cards.Target;

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
	private boolean hasTarget;
	private Target target;

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
	
	/**
	 * Returns the description as a String
	 * @return
	 * 		String description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Returns true if this ability is a targetedability and false if this
	 * abiltity doesn't require a target.
	 * 
	 * @return hasTarget : boolean
	 */
	public boolean hasTarget() {
		return hasTarget;
	}
	
	public void setHasTarget(boolean hasTarget){
		this.hasTarget=hasTarget;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	
	public Target getTarget(){
		return target;
	}
}
