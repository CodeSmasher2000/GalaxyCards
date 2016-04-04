package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
	
	public void testHeal() {
		Heal test = new Heal();
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
	
	private class Heal implements TestCase, ActionListener {
		private JButton btnHeal;
		private JButton btnDamage;
		private Unit unit;
		private JPanel pnlButtons;
		private JFrame cardFrame;
		private int amtToHeal;
		
		public void initGui() {
			btnHeal = new JButton("Heal");
			btnHeal.addActionListener(this);
			btnDamage = new JButton("Damage (1)");
			btnDamage.addActionListener(this);
			pnlButtons = new JPanel();
			pnlButtons.add(btnHeal);
			pnlButtons.add(btnDamage);
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					JFrame btnframe = new JFrame();
					btnframe.add(pnlButtons);
					btnframe.pack();
					btnframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					btnframe.setVisible(true);
					
					
					cardFrame = new JFrame();
					cardFrame.add(unit);
					cardFrame.pack();
					cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					cardFrame.setVisible(true);
				}
			});
		}
		
		@Override
		public void setup() {
			amtToHeal = 
					Integer.parseInt(JOptionPane.showInputDialog("Enter the ammount to heal"));
			unit = new Unit("Test", "Common", "Spaceship1", false, 3, 3, 3);
			initGui();
		}

		@Override
		public void runTest() {
			
		}

		@Override
		public void reset() {
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnDamage) {
				unit.setDefense(-1);
			} else if(e.getSource() == btnHeal) {
				Rules.getInstance().heal(unit,amtToHeal);
			}
		}
		
	}
	
	public static void main(String[] args) {
		// Test for The Rule Draw Card
		TestRules prog = new TestRules();
//		prog.testDrawCard();
		prog.testHeal();
	}
	
}
