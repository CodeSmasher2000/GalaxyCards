package abilities;

import enumMessage.Lanes;

public class UntapTargetAbility extends Ability{

	private int target;
	private Lanes ENUM;
	
	public UntapTargetAbility(String description){
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
