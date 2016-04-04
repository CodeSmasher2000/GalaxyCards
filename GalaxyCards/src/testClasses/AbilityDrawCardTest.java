package testClasses;

import abilities.Ability;
import abilities.DrawCard;
import board.Board;
import cards.Deck;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import game.Controller;
import game.Rules;
import guiPacket.Card;

import javax.swing.*;

/**
 * A test class for the ability Draw Card
 * @author patriklarsson
 *
 */
public class AbilityDrawCardTest {
    private Unit unit;

    public void setup() {
        // Creates A Deck with 5 resource cards
        Deck deck = new Deck();
        for (int i = 0; i < 5 ; i++) {
            deck.addResoruceCard(new ResourceCard());
        }

        // Creates A Board
        Board board = new Board(deck);
        Rules.getInstance().setController(new Controller(board));

        unit = new Unit("Test", "Common", "Spaceship1", true, 3, 3, 3);
        Ability drawCard = new DrawCard("Draw one card", 1);
        unit.setAbility(drawCard);
        unit.setAbilityText(drawCard.toString());
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

    public static void main(String[] args) {
        AbilityDrawCardTest test = new AbilityDrawCardTest();
        test.setup();
        test.initGui();
    }
}
