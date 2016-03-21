package cards;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
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
