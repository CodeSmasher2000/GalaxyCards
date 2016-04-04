package cards;

import java.io.Serializable;

import javax.swing.JOptionPane;

import guiPacket.HeroGUI;

/**
 * This class represents the player's hero and is responsible for a visual
 * representation of the hero and his attributes. This class is also responsible
 * for altering the hero's attributes aswell as determinating if the player has
 * lost the match. 
 * 
 * @author 13120dde
 *
 */
//TODO make algoritm for resourcemanagment.
public class Hero extends HeroGUI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -704227521994333558L;
	private int life, energyShield, maxResource, currentResource;

	public Hero(String heroName) {
		super(heroName);
		life = 20;
		energyShield = 10;
		maxResource=0;
		currentResource=0;
	}

	/**
	 * Deal damage to this hero. The damage is dealt first to the hero's energy
	 * shield. If the shield is depleted the damage is dealt to the hero's
	 * life. If the damage is higher than the amount of energy shield, any
	 * excessive damage won't go trough to the hero's life.
	 * 
	 * @param damage
	 */
	public void dealDamage(int damage) {
		if (energyShield > 0) {
			energyShield -= damage;
			updateShiledBar(energyShield);
		} else {
			energyShield=0;
			life -= damage;
			updateLifeBar(life);
			updateShiledBar(energyShield);
		}

		// TODO update gui
		if (life <= 0) {
			youLoose();
			JOptionPane.showMessageDialog(null, "YOU LOOSE!");
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
		updateShiledBar(energyShield);
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
		} else {
			energyShield -= amount;
			updateShiledBar(energyShield);
			return true;
		}

	}
	
	public String toString(){
		return "life: "+life+"\nShield: "+energyShield;
	}

	private void youLoose() {
		// TODO Auto-generated method stub
		// TODO Update gui
		// TODO send info to the otherplayer
	}

}
