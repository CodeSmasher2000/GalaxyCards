package abilities;

public abstract class Ability {

	private String description;
	
	
	public String toString(){
		return description;
	}
	
	public void setDescription(String txt){
		description=txt;
	}
}
