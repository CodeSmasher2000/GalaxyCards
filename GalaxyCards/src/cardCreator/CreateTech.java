package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateTech extends JPanel {
	private JPanel gridTech = new JPanel();

	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNbrOfCards = new JTextField();
	private JTextArea tfDescription = new JTextArea();

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	public CreateTech(){
		gridTech.setLayout(new GridLayout(4,2));
		gridTech.setPreferredSize(new Dimension(400,600));
		gridTech.add(lblCardName);
		gridTech.add(tfCardName);
		gridTech.add(lblPrice);
		gridTech.add(tfPrice);
		gridTech.add(lblDescription);
		gridTech.add(tfDescription);
		gridTech.add(lblNbrOfCards);
		gridTech.add(tfNbrOfCards);
		add(gridTech);
	}
	
	public int getPrice() {
		// TODO: Error Handling
		return Integer.parseInt(tfPrice.getText());
	}
	
	public String getName() {
		// TODO: Error Handling
		return tfCardName.getText();
	}
	
	public String getDescription() {
		return tfDescription.getText();
	}

	public String gerRarity() {
		// TODO Hämta från Jonatans kod
		return null;
	}

	public String getImageName() {
		// TODO Auto-generated method stub
		return null;
	}
}
