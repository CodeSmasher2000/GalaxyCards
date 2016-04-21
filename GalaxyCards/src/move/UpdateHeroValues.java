package move;

import java.io.Serializable;

public class UpdateHeroValues implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentResource;
	private int maxResource;
	private int life;
	private int energyShield;

	public UpdateHeroValues(int newLife, int newEnergyShield, int newCurrentResource, int newMaxResource) {

		setCurrentResource(newCurrentResource);
		setMaxResource(newMaxResource);
		setLife(newLife);
		setEnergyShield(newEnergyShield);
		
	}

	public int getCurrentResource() {
		return currentResource;
	}

	public void setCurrentResource(int currentResource) {
		this.currentResource = currentResource;
	}

	public int getMaxResource() {
		return maxResource;
	}

	public void setMaxResource(int maxResource) {
		this.maxResource = maxResource;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getEnergyShield() {
		return energyShield;
	}

	public void setEnergyShield(int energyShield) {
		this.energyShield = energyShield;
	}

}
