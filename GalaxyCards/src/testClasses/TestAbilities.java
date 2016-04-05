package testClasses;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import abilities.Ability;
import abilities.DrawCard;
import board.Board;
import cards.Deck;
import cards.ResourceCard;
import cards.Unit;
import game.Controller;
import game.Rules;

/**
 * This class contatins methods to test abilites
 * 
 * @author patriklarsson
 *
 */
public class TestAbilities {
	
	public void DrawCardTest() {
		TestCase test = new AbilityDrawCardTest();
		test.setup();
		test.runTest();
		test.reset();
	}
	
	private class AbilityDrawCardTest implements TestCase {
		private Unit unit;

		public void setup() {
			// Creates A Deck with 5 resource cards
			Deck deck = new Deck();
			for (int i = 0; i < 5; i++) {
				deck.addResoruceCard(new ResourceCard());
			}

			// Creates A Board
			Board board = new Board(deck);
			Rules.getInstance().setController(new Controller(board));

			unit = new Unit("Test", "Common", "Spaceship1", true, 3, 3, 3);
			Ability drawCard = new DrawCard("Draw one card", 1);
			unit.setAbility(drawCard);
			unit.setAbilityText(drawCard.toString());
			initGui();
		}

		public void initGui() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new JFrame("Card Frame");
					frame.add(unit);
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			});
		}

		@Override
		public void runTest() {
			// Not used in this test case

		}

		@Override
		public void reset() {
			// Not used in this test case
		}
	}
	
	private class AbilityDamageTest implements TestCase {

		@Override
		public void setup() {
			// TODO Auto-generated method stub
			
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
		TestAbilities prg = new TestAbilities();
		prg.DrawCardTest();
	}
}
