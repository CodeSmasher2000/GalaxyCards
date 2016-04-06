package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.omg.CORBA.PUBLIC_MEMBER;

import board.Board;
import cards.Deck;
import cards.ResourceCard;
import cards.Unit;
import exceptionsPacket.NotValidMove;
import game.Controller;
import game.Hero;
import game.Rules;
import guiPacket.BoardGuiController;
import guiPacket.HandGUI;

/**
 * This class should contatin test methods for the rules class
 * 
 * @author patriklarsson
 *
 */
public class TestRules {
	private Controller controller;
	private Board board;

	public TestRules() {
		board = new Board(new Deck());
		controller = new Controller(board, new BoardGuiController());
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

	public void testTarget() {
		TestTarget test = new TestTarget();
		test.setup();
		test.runTest();
		test.reset();
	}
	
	public void testPlayCard() throws TestFailedException {
		PlayCard test = new PlayCard();
		test.setup();
		test.runTest();
		test.reset();
	}

	private class DrawCard implements TestCase {
		private Unit unit1 = new Unit("1", "Common", "starship1", false, 3, 3, 3);
		private Unit unit2 = new Unit("2", "Common", "starship1", false, 3, 3, 3);
		private Unit unit3 = new Unit("3", "Common", "starship1", false, 3, 3, 3);
		private Unit unit4 = new Unit("4", "Common", "starship1", false, 3, 3, 3);
		private Unit unit5 = new Unit("5", "Common", "starship1", false, 3, 3, 3);

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
			while (board.getPlayerDeck().getAmtOfCards() > 0) {
				Rules.getInstance().drawCard();
			}

			board.printHand();

			Rules.getInstance().drawCard();
		}

		public void reset() {
			TestRules.this.reset();
		}
	}

	private class PlayCard implements TestCase {
		private Hero friendlyHero;
		private Unit test1;
		private Unit test2;

		@Override
		public void setup() {
			friendlyHero = new Hero("Test");
			controller.initGame(friendlyHero, null);

			// Add five reosurce Cards to the hand
			for (int i = 0; i < 5; i++) {
				controller.addCardToHand(new ResourceCard());
			}

			// Adds Two Unit cards to the hand
			test1 = new Unit("Test1", "Common", "Spaceship1", false, 2, 2, 2);
			test2 = new Unit("Test2", "Common", "Spaceship2", false, 3, 3, 3);
			controller.addCardToHand(test1);
			controller.addCardToHand(test2);

			// Test Fails if Heroes Resource is not 0
			if (friendlyHero.getCurrentResource() != 0) {
				try {
					throw new TestFailedException("Hjätens Resurs är inte 0");
				} catch (TestFailedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void runTest() throws TestFailedException {
			// Tries to play a resource card
			try {
				Rules.getInstance().playCard(controller.getCardFromHand(0));
			} catch (NotValidMove e) {
				try {
					throw new TestFailedException("Resurs Kort gick inte att spela");
				} catch (TestFailedException e1) {
					e1.printStackTrace();
				}
			}

			if (friendlyHero.getCurrentResource() != 1) {
				throw new TestFailedException("Hjätens resurs är inte 1");
			}

			System.out.println("Hjätens resurs är : " + friendlyHero.getCurrentResource());

			// Tries to play another resource card
			try {
				Rules.getInstance().playCard(controller.getCardFromHand(0));
			} catch (NotValidMove e) {
				throw new TestFailedException("Resurs Kort gick inte att spela");
			}

			if (friendlyHero.getCurrentResource() != 2) {
				throw new TestFailedException("Hjätens resurs är inte 2");
			}
			System.out.println("Hjältens resus är 2");
			
			// Tries to play a unit card should be sucessfull if the test should
			// pass
			try {
				Rules.getInstance().playCard(controller.getCardFromHand(3));
			} catch (NotValidMove e) {
				e.printStackTrace();
				throw new TestFailedException("Unit kort gick inte att spela");
			}
			board.printHand();
			// Tries to play a unid card this should fail if the test is sucessfull
			try {
				Rules.getInstance().playCard(controller.getCardFromHand(3));
			} catch(NotValidMove e) {
			}
			
			// The unit card should be in the hand
			if (!controller.getCardFromHand(3).equals(test2)) {
				throw new TestFailedException("Test2 är inte kvar i handen");
			}
			
			System.out.println("Test passed");

		}

		@Override
		public void reset() {
			TestRules.this.reset();
		}

	}

	private class TestTarget implements TestCase, ActionListener {
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
			amtToHeal = Integer.parseInt(JOptionPane.showInputDialog("Enter the ammount to heal"));
			unit = new Unit("Test", "Common", "Spaceship1", false, 3, 3, 3);
			initGui();
		}

		@Override
		public void runTest() {

		}

		@Override
		public void reset() {
			TestRules.this.reset();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnDamage) {
				Rules.getInstance().damage(unit, 1);
			} else if (e.getSource() == btnHeal) {
				Rules.getInstance().heal(unit, amtToHeal);
			}
		}

	}
	
	public static void main(String[] args) {
		// Test for The Rule Draw Card
		TestRules prog = new TestRules();
//		prog.testDrawCard();
		// prog.testTarget();
		
		try {
			prog.testPlayCard();
		} catch (TestFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
