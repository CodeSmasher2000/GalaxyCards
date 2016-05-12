package abilities;

import enumMessage.Lanes;

public class TapTargetAbility extends Ability {

	private int target;
	private Lanes ENUM;
	
	public TapTargetAbility(String description){
		setDescription(description);
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public Lanes getENUM() {
		return ENUM;
	}

	public void setENUM(Lanes eNUM) {
		ENUM = eNUM;
	}
	
	
}
