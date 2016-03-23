package cardCreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import guiPacket.*;
import cards.Deck;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import cardCreator.CreateHeroic;
//TODO: Add Margins to the list

public class DeckPanel extends JPanel {
	private DefaultListModel<Card> listModel = new DefaultListModel<Card>();
	private JList<Card> list;
	private JButton btnSelect;
	private JButton btnRemove;
	private JButton btnLoadDeck;
	private JButton btnSaveDeck;
	private CreateController controller;
	private JLabel lblHeroic;
	private JLabel lblTech;
	private JLabel lblResource;
	private JLabel lblUnit;
	private int heroicNbr = 0;
	private int unitNbr = 0;
	private int resourceNbr = 0;
	private int techNbr = 0;
	
	public DeckPanel(CreateController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		initList();
		initButtons();
		initNorthPanel();
	}
	
	private void initButtons() {
		ButtonListener listener = new ButtonListener();
		JPanel btnPanel = new JPanel();
		btnSelect = new JButton("Select Card");
		btnSelect.addActionListener(listener);
		btnRemove = new JButton("Remove Card");
		btnRemove.addActionListener(listener);
		btnSaveDeck = new JButton("Save Deck");
		btnSaveDeck.addActionListener(listener);
		btnLoadDeck = new JButton("Load Deck");
		btnLoadDeck.addActionListener(listener);
		btnPanel.add(btnSelect);
		btnPanel.add(btnRemove);
		btnPanel.add(btnSaveDeck);
		btnPanel.add(btnLoadDeck);
		add(btnPanel, BorderLayout.SOUTH);
	}
	
	private void initList() {
		list = new JList<Card>();
		list.setModel(listModel);
		list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addToListModel(Card cardToAdd) {
		listModel.addElement(cardToAdd);
		increaseCard(cardToAdd);
	}
	
	public void removeFromList(Card toRemove) {
		listModel.removeElement(toRemove);
		decreaseCard(toRemove);
		}
	
	private void decreaseCard(Card toRemove) {
		if (toRemove instanceof HeroicSupport) {
			heroicNbr--;
			lblHeroic.setText("Heroic: " + heroicNbr);
		}
		else if (toRemove instanceof Unit) {
			unitNbr--;
			lblUnit.setText("Unit: " + unitNbr);
		}
		else if (toRemove instanceof ResourceCard) {
			resourceNbr--;
			lblResource.setText("Resource: " + resourceNbr);
		}
		else if (toRemove instanceof Tech) {
			techNbr--;
			lblTech.setText("Tech: " + techNbr);
		}
		
	}

	public void initNorthPanel() {
		JPanel showCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
		this.add(showCardsPanel, BorderLayout.NORTH);
		lblHeroic = new JLabel("Heroic:");
		lblTech = new JLabel("Tech:");
		lblResource = new JLabel("Resource:");
		lblUnit = new JLabel("Unit:");
		showCardsPanel.add(lblHeroic);
		showCardsPanel.add(lblResource);
		showCardsPanel.add(lblTech);
		showCardsPanel.add(lblUnit);
	}
	
	public void increaseCard(Card cardToIncrease) {
		if (cardToIncrease instanceof HeroicSupport) {
			heroicNbr++;
			lblHeroic.setText("Heroic: " + heroicNbr);
		}
		else if (cardToIncrease instanceof Unit) {
			unitNbr++;
			lblUnit.setText("Unit: " + unitNbr);
		}
		else if (cardToIncrease instanceof ResourceCard) {
			resourceNbr++;
			lblResource.setText("Resource: " + resourceNbr);
		}
		else if (cardToIncrease instanceof Tech) {
			techNbr++;
			lblTech.setText("Tech: " + techNbr);
		}
		
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRemove) {
				controller.removeCardFromList(list.getSelectedValue());
			} else if (e.getSource() == btnSelect) {
				controller.listItemSelected(list.getSelectedValue());
			} else if(e.getSource() == btnSaveDeck) {
				controller.saveDeckToFile();
			} else if(e.getSource() == btnLoadDeck) {
				controller.readDeckFromFile();
			}
			
		}
		
	}
	
}
