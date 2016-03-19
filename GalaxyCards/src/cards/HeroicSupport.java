package cards;

import javax.swing.JFrame;

public class HeroicSupport extends Card{
	
	private int defense; 
	
	public HeroicSupport(){
		setName("Star Destroyer");
		setType(this);
	}
	
	/**Instantiates the card with given arguments String name, int defense, int price
	 * 
	 * @param name : String
	 * @param defense : int
	 * @param price : int
	 */
	public HeroicSupport(String name, int defense, int price){
		setType(this);
		setName(name);
		setPrice(price);
		setDefense(defense);
		setRarity("legendary");
	}

	public static void main(String[] args) {
		HeroicSupport card = new HeroicSupport();
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(card);
		frame.pack();
		
	}
}
