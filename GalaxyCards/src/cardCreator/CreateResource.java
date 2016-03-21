package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateResource extends JPanel {
	private JPanel gridResource = new JPanel();
	
	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNbrOfCards = new JTextField();
	private JTextArea tfDescription = new JTextArea();

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	public CreateResource(){
		gridResource.setLayout(new GridLayout(4,2));
		gridResource.setPreferredSize(new Dimension(400,600));
		gridResource.add(lblCardName);
		gridResource.add(tfCardName);
		gridResource.add(lblPrice);
		gridResource.add(tfPrice);
		gridResource.add(lblDescription);
		gridResource.add(tfDescription);
		gridResource.add(lblNbrOfCards);
		gridResource.add(tfNbrOfCards);
		add(gridResource);
	}
}
