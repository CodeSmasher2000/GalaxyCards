package cards;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import exceptionsPacket.EmptyDeckException;
import game.Hero;
import guiPacket.Card;

public class Deck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4951209232481964562L;
	private LinkedList<Card> deck = new LinkedList<Card>();
	private int nbrOfUnitCards = 0;
	private int nbrOfResourceCards = 0;
	private int nbrOfHeroicSupport = 0;
	private int nbrOfTech = 0;

	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card drawCard() throws EmptyDeckException{
		if(!deck.isEmpty()){
			return deck.removeFirst();
		}else{
			throw new EmptyDeckException("Empty deck, can't draw more cards.");
		}
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
		if (deck.size() <= 60) {
			this.deck.add(card);
		} else {
			// TODO: Throw Exception
		}
	}
	
	public void addUnitCard(Unit card) {
		deck.add(card);
		nbrOfUnitCards++;
	}
	
	public void addTechCard(Tech cardToAdd) {
		deck.add(cardToAdd);
		nbrOfTech++;
		
	}
	
	public void addHeroicSupportCard(HeroicSupport cardToAdd) {
		deck.add(cardToAdd);
		nbrOfHeroicSupport++;
	}
	
	public int getAmtOfCards() {
		return deck.size();
	}
	
	public Card getCard(int index) {
		return deck.get(index);
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
	
	public void removeCard() {
		Card toRemove = deck.remove();
		if (toRemove instanceof Unit)  {
			nbrOfUnitCards--;
		} else if(toRemove instanceof HeroicSupport) {
			nbrOfHeroicSupport--;
		} else if(toRemove instanceof Tech) {
			nbrOfTech--;
		} else if (toRemove instanceof ResourceCard) {
			nbrOfResourceCards--;
		}
	}

	public void setNbrOfResourceCards(int nbrOfResourceCards) {
		this.nbrOfResourceCards = nbrOfResourceCards;
	}
	
	public String toString(){
		return "Deck: "+super.toString()+ "\nResources: "+nbrOfResourceCards+"\nUnits: "+nbrOfUnitCards+"\nHeroic Support: "+nbrOfHeroicSupport+"\nTech: "+nbrOfTech;
	}
}
