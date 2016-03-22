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
	private int amtOfCards = 0;
	private int nbrOfUnitCards = 0;
	private int nbrOfResourceCards = 0;
	private int nbrOfHeroicSupport = 0;
	private int nbrOfTech = 0;

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
	
	public void addResoruceCard(ResourceCard card) {
		System.out.println("Add resoruce card");
		if (nbrOfResourceCards < 20) {
			addCard(card);
			nbrOfResourceCards++;
		} else {
			// TODO: Throw Exception
		}
	}
	
	public void addCard(Card card) {
		if (amtOfCards < 60) {
			this.deck.add(card);
		} else {
			// TODO: Throw Exception
		}
	}
	
	public void addUnitCard(Unit card) {
		deck.add(card);
	}
	
	public void addTechCard(Tech cardToAdd) {
		deck.add(cardToAdd);
		
	}
	
	public void addHeroicSupportCard(HeroicSupport cardToAdd) {
		deck.add(cardToAdd);
	}
	
	public int getAmtOfCards() {
		return amtOfCards;
	}
	
	public Card getCard(int index) {
		return deck.get(index);
	}

	public void setAmtOfCards(int amtOfCards) {
		this.amtOfCards = amtOfCards;
	}
	
	public int getNbrOfResourceCards() {
		return nbrOfResourceCards;
	}
	
	public void removeCard(Card toRemove) {
		if (toRemove instanceof Unit)  {
			deck.remove(toRemove);
			nbrOfUnitCards--;
		} else if(toRemove instanceof HeroicSupport) {
			deck.remove(toRemove);
			nbrOfHeroicSupport--;
		} else if(toRemove instanceof Tech) {
			deck.remove(toRemove);
			nbrOfTech--;
		} else if (toRemove instanceof ResourceCard) {
			deck.remove(toRemove);
			nbrOfResourceCards--;
		}
	}

	public void setNbrOfResourceCards(int nbrOfResourceCards) {
		this.nbrOfResourceCards = nbrOfResourceCards;
	}
}
