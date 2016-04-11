package game;

import java.io.Serializable;

import javax.naming.InsufficientResourcesException;

/**
 * This class represents the player's hero and is responsible for a logical
 * representation of the hero and his attributes. This class is also responsible
 * for altering the hero's attributes aswell as determinating if the player has
 * lost the match.
 * 
 * @author 13120dde
 *
 */
// TODO refactor, this class should not communicate with HeroGUI directly,
// values should be sent trough controller.

public class Hero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -704227521994333558L;
	private int life, energyShield, maxResource, currentResource;
	private boolean resourcecardPlayed = false;

	public Hero(String heroName) {
		life = 20;
		energyShield = 10;
		maxResource = 0;
		currentResource = 0;
	}

	/**
	 * Deal damage to this hero. The damage is dealt first to the hero's energy
	 * shield. If the shield is depleted the damage is dealt to the hero's life.
	 * If the damage is higher than the amount of energy shield, any excessive
	 * damage won't go trough to the hero's life.
	 * 
	 * @param amount
	 */
	public void dealDamage(int amount) {
		if (energyShield > 0) {
			energyShield -= amount;

		} else {
			energyShield = 0;
			life -= amount;

			// Debugg
			// updateLifeBar(life);
			// updateShiledBar(energyShield);
		}

	}

	/**
	 * Add x amount of energy shield to this hero. The shield's max amount is
	 * 10.
	 * 
	 * @param amount
	 */
	public void addShield(int amount) {
		energyShield = energyShield + amount;
		if (energyShield > 10) {
			energyShield = 10;
		}

		// DEBUGG
		// updateShiledBar(energyShield);
	}
	

	/**
	 * Attempts to remove x amount of energy shield from the hero. If x is
	 * higher than the amount of shield then nothing will be removed and false
	 * is returned. Else removes x from shield and returns true.
	 * 
	 * @param amount
	 * @return
	 */
	public boolean removeShield(int amount) {
		if (energyShield == 0 || energyShield < amount) {
			return false;
			// throw exception that is catched in the object which tries to
			// activate the ability
		} else {
			energyShield -= amount;

			// DEBUGG
			// updateShiledBar(energyShield);
			return true;
		}

	}

	public int getLife(){
		return life;
	}
	
	public int getEnergyShield(){
		return energyShield;
	}
	
	public void addResource(){
		if(!resourcecardPlayed){
		maxResource++;
		currentResource++;
		}else{
			throw new ResourcePlayedException("You can only play one resource card each turn");
		}
	}
	
	public void useResource(int amount) throws InsufficientResourcesException{
		if(amount>currentResource){
			throw new InsufficientResourcesException("Insufficient resources");
		}
		currentResource-=amount;
	}
	
	public void resetResources(){
		currentResource=maxResource;
	}
	
	public String toString() {
		return "life: " + life + "\nShield: " + energyShield + "\n current resource: " + currentResource + " / "
				+ maxResource;
	}

	private void youLoose() {
		// TODO Auto-generated method stub
		// TODO Update gui
		// TODO send info to the otherplayer
	}

}
