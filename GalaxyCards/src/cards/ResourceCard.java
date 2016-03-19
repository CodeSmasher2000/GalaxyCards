package cards;

import javax.swing.JFrame;

public class ResourceCard extends Card {
	
	private final int RESOURCE = 1;
	
	public ResourceCard(){
		setName("Star power");
		setImage("resource");
		setRarity("common");
		hasAbility(false);
		setAbilityText("Gain 1 resource");
		setType(this);
	}
	
	public int getResource(){
		return RESOURCE;
	}

}
