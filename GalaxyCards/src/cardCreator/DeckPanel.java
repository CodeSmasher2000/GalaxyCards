package cardCreator;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import cards.Card;
import cards.HeroicSupport;
//TODO: Add Margins to the list

public class DeckPanel extends JPanel {
	DefaultListModel<Card> listModel = new DefaultListModel<Card>();
	JList<Card> list;
	
	public DeckPanel() {
		list = new JList<Card>();
		list.setModel(listModel);
		list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);
	}
	
	public void addToListModel(Card cardToAdd) {
		listModel.addElement(cardToAdd);
	}
	
	public void removeFromList(int index) {
		listModel.remove(index);
	}
	
	public static void main(String[] args) {
		DeckPanel list = new DeckPanel();
		for (int i = 0; i < 60; i++) {
			list.addToListModel(new HeroicSupport("Commander", "common", "test", false, 5, 3));
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Jonte");
				frame.add(list);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
}
