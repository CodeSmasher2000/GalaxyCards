package cardCreator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import guiPacket.*;
import cards.HeroicSupport;
//TODO: Add Margins to the list

public class DeckPanel extends JPanel {
	private DefaultListModel<Card> listModel = new DefaultListModel<Card>();
	private JList<Card> list;
	private JButton btnSelect;
	private JButton btnRemove;
	private CreateController controller;
	
	public DeckPanel(CreateController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		initList();
		initButtons();
	}
	
	private void initButtons() {
		ButtonListener listener = new ButtonListener();
		JPanel btnPanel = new JPanel();
		btnSelect = new JButton("Select Card");
		btnSelect.addActionListener(listener);
		btnRemove = new JButton("Remove Card");
		btnRemove.addActionListener(listener);
		btnPanel.add(btnSelect);
		btnPanel.add(btnRemove);
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
	}
	
	public void removeFromList(Card toRemove) {
		listModel.removeElement(toRemove);
		}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnRemove) {
				controller.removeCardFromList(list.getSelectedValue());
			} else if (e.getSource() == btnSelect) {
				controller.listItemSelected(list.getSelectedValue());
			}
			
		}
		
	}
	
}
