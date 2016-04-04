package testClasses;

import javax.swing.JOptionPane;

import board.Board;
import cards.Deck;
import cards.Unit;
import game.Controller;
import game.Rules;
import guiPacket.HandGUI;

/**
 * This class should contatin test methods for the rules class
 * @author patriklarsson
 *
 */
public class TestRules {
	private Controller controller;
	private Board board;
	
	public TestRules() {
		board = new Board(new Deck());
		controller = new Controller(board);
		Rules.getInstance().setController(controller);
	}
	
	public void reset() {
		// Removes All Card in the deck
		while (board.getPlayerDeck().getAmtOfCards() != 0) {
			board.getPlayerDeck().removeCard();
		}
		
		// Removes All The cards in the playerHand
		board.clearHand();
	}
	/**
	 * This class contains methods for setting up a test for Draw Card rule
	 */
	public void testDrawCard() {
		DrawCard test = new DrawCard();
		test.setup();
		test.runTest();
		test.reset();
	}
	
	private class DrawCard implements TestCase {
		private Unit unit1 = new Unit("1", "Common", "starship1", false,
				3, 3, 3);
		private Unit unit2 = new Unit("2", "Common", "starship1", false,
				3, 3, 3);
		private Unit unit3 = new Unit("3", "Common", "starship1", false,
				3, 3, 3);
		private Unit unit4 = new Unit("4", "Common", "starship1", false,
				3, 3, 3);
		private Unit unit5 = new Unit("5", "Common", "starship1", false,
				3, 3, 3);
		
		public void setup() {
			// Adds Five Cards to the deck
			board.getPlayerDeck().addCard(unit1);
			board.getPlayerDeck().addCard(unit2);
			board.getPlayerDeck().addCard(unit3);
			board.getPlayerDeck().addCard(unit4);
			board.getPlayerDeck().addCard(unit5);
		}
		
		public void runTest() {
			board.printHand();
			
			// Tries to draw a card from the deck unit it´s empty
			while(board.getPlayerDeck().getAmtOfCards() > 0) {
				Rules.getInstance().drawCard();
			}
			
			board.printHand();
			
			
			Rules.getInstance().drawCard();
		}
		
		public void reset() {
			TestRules.this.reset();
		}
	}
	
	private class Heal implements TestCase {
		
		@Override
		public void setup() {
			int amtToHeal = 
					Integer.parseInt()
			
		}

		@Override
		public void runTest() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String[] args) {
		// Test for The Rule Draw Card
		TestRules prog = new TestRules();
		prog.testDrawCard();
	}
	
}