package cards;

import javax.swing.JFrame;

public class Unit extends Card {

	private int attack, defense, price;
	
	public Unit() {
		setType(this);
		setName("Battle cuiser");
		setAttack(5);
		setDefense(8);
		setPrice(6);
	}

	/**
	 * Instantiates the card with given arguments String name, int attack, int
	 * defense, int price
	 * 
	 * @param name
	 *            : String
	 * @param attack
	 *            : int
	 * @param defense
	 *            : int
	 * @param price
	 *            : int
	 */
	public Unit(String name, int attack, int defense, int price) {
		this.attack = attack;
		this.defense = defense;
		this.price = price;
		setType(this);
		setName(name);
		setAttack(attack);
		setDefense(defense);
		setPrice(price);
	}
	
	public void setAttack(int attack){
		this.attack=attack;
	}
	
	public void setDefense(int defense){
		this.defense=defense;
	}
	public void setPrice(int price){
		this.price=price;
	}
	
	public int getAttack(){
		return attack;
	}
	
	public int getDefense(){
		return defense;
	}
	
	public int getPrice(){
		return price;
	}
}
