package cardCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import guiPacket.*;
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
	
	// Variables needed to communicate with the rest of the gui
	private CreateController controller;
	private CreateGui createGui;
	
	/**
	 * Sets up the panel
	 * @param controller
	 * 		:CreateController
	 * @param createGui
	 * 		:CreateGui
	 */
	public CardPane(CreateController controller, CreateGui createGui) {
		this.controller = controller;
		this.createGui = createGui;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 240));
		initCardPanel();
		initButtons();
	}
	
	/**
	 * Sets the card to display
	 * @param card
	 */
	public void setCard(Card card) {
		cardPanel.removeAll();
		initCardPanel();
		cardPanel.add(card);
		this.cardPanel.revalidate();
	}
	
	private void initCardPanel() {
		cardPanel.setPreferredSize(new Dimension(200, 150));
		add(cardPanel, BorderLayout.CENTER);
	}
	
	private void initButtons() {
		Dimension buttonSize = new Dimension(100, 25);
		JPanel btnPanel = new JPanel();
		ButtonListener btnListner = new ButtonListener();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPreview = new JButton("Preview");
		btnPreview.setPreferredSize(buttonSize);
		btnAdd = new JButton("Add Card");
		btnAdd.setPreferredSize(buttonSize);
		btnPanel.add(btnPreview);
		btnPanel.add(btnAdd);
		add(btnPanel, BorderLayout.SOUTH);
		btnAdd.addActionListener(btnListner);
		btnPreview.addActionListener(btnListner);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnPreview) {
				controller.previewCard();
			} else if(e.getSource() == btnAdd) {
				createGui.addCard();
			}
			
		}
		
	}
	

}
