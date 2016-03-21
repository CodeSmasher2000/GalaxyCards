package cardCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cards.Card;
import cards.HeroicSupport;

/**
 * The class is used to create a panel with two JButtons and a Card to have a preview in the create a Card
 * Application
 * @author patriklarsson
 *
 */
public class CardPane extends JPanel {
	private JButton btnPreview;
	private JButton btnAdd;
	private JPanel cardPanel = new JPanel();
	
	/**
	 * Sets the card to display
	 * @param card
	 */
	public void setCard(Card card) {
		cardPanel.add(card);
	}
	
	public CardPane() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 240));
		initCardPanel();
		initButtons();
	}
	
	private void initCardPanel() {
		cardPanel.setPreferredSize(new Dimension(200, 150));
		add(cardPanel, BorderLayout.CENTER);
	}
	
	private void initButtons() {
		Dimension buttonSize = new Dimension(100, 25);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPreview = new JButton("Preview");
		btnPreview.setPreferredSize(buttonSize);
		btnAdd = new JButton("Add Card");
		btnAdd.setPreferredSize(buttonSize);
		btnPanel.add(btnPreview);
		btnPanel.add(btnAdd);
		add(btnPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		CardPane pane = new CardPane();
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.add(pane);
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		Card heroicSupport2 = new HeroicSupport("Overlord", "rare", "test", true, 7, 5);
		pane.setCard(heroicSupport2);
	}
	

}
