package abilities;

import enumMessage.Lanes;

public class SingleTargetAbility extends Ability{
	
	private int target;
	private int value;
	private Lanes ENUM;
	
	public SingleTargetAbility(int value, String description){
		setValue(value);
		setDescription(description);
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Lanes getENUM() {
		return ENUM;
	}

	public void setENUM(Lanes eNUM) {
		ENUM = eNUM;
	}

}
