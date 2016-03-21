package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Creates a panel that lets you customize a unit card.
 * @author Jonte
 *
 */
public class CreateUnit extends JPanel {
	private JPanel gridUnit = new JPanel();
	
	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfAttack =  new JTextField();
	private JTextField tfDefense = new JTextField();
	private JTextField tfRarity = new JTextField("Common/Rare/Legendary");
	private JTextField tfNbrOfCards = new JTextField();
	private JCheckBox btnAbility = new JCheckBox();
	private JTextArea taDescription = new JTextArea();

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblAttack = new JLabel("Attack: ");
	private JLabel lblDefense = new JLabel("Defense: ");
	private JLabel lblRarity = new JLabel ("Rarity: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblAbility = new JLabel("Ability: Yes/No");
	private JLabel lblDescription = new JLabel("Description");
	
	
	/**
	 * adds the components in a GridLayout panel and adds that panel to the main panel
	 */
	public CreateUnit(){
		gridUnit.setLayout(new GridLayout(8,2));
		gridUnit.setPreferredSize(new Dimension(400,600));
		gridUnit.add(lblCardName);
		gridUnit.add(tfCardName);
		gridUnit.add(lblAttack);
		gridUnit.add(tfAttack);
		gridUnit.add(lblDefense);
		gridUnit.add(tfDefense);
		gridUnit.add(lblPrice);
		gridUnit.add(tfPrice);
		gridUnit.add(lblRarity);
		gridUnit.add(tfRarity);
		gridUnit.add(lblAbility);
		gridUnit.add(btnAbility);
		gridUnit.add(lblDescription);
		gridUnit.add(taDescription);
		gridUnit.add(lblNbrOfCards);
		gridUnit.add(tfNbrOfCards);
		add(gridUnit);
		
		

	}

}
