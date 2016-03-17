package cards;

import javax.swing.JFrame;

public class HeroicSupport extends Card{
	
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
	}

	public static void main(String[] args) {
		HeroicSupport card = new HeroicSupport();
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(card);
		frame.pack();
		
	}
}
