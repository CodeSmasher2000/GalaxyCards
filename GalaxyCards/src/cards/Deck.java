package cards;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import guiPacket.Card;

public class Deck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4951209232481964562L;
	private LinkedList<Card> deck = new LinkedList<Card>();
	private int damage=1;
	private Hero hero;
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card drawCard(){
		if(!deck.isEmpty()){
			return deck.getFirst();
		}else{
			hero.dealDamage(incrementalDamage());
			return null;
		}
	}
	
	public int incrementalDamage(){
		return damage++;
	}
	
	public void addCard(Card card) {
		deck.add(card);
	}


}
