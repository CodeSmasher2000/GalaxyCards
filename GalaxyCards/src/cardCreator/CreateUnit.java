package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


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
	private JFileChooser jFileChooser = new JFileChooser();
	private JButton btnChoosePic = new JButton("Choose Picture");

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblAttack = new JLabel("Attack: ");
	private JLabel lblDefense = new JLabel("Defense: ");
	private JLabel lblRarity = new JLabel ("Rarity: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblAbility = new JLabel("Ability: Yes/No");
	private JLabel lblDescription = new JLabel("Description");
	
	private String imageName = null;
	
	
	/**
	 * adds the components in a GridLayout panel and 
	 */
	public CreateUnit(){
		gridUnit.setLayout(new GridLayout(9,2));
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
		
		ButtonListener btnListener = new ButtonListener();
		btnChoosePic.addActionListener(btnListener);
		gridUnit.add(btnChoosePic);
		add(gridUnit);
		
		
	}
	public boolean getAbility() {
		return btnAbility.isSelected();
	}
	
	public int getPrice() {
		// TODO: Error Handling
		return Integer.parseInt(tfPrice.getText());
	}
	
	public int getAttack() {
		// TODO: Error Handling
		return Integer.parseInt(tfAttack.getText());
	}
	
	public int getDefense() {
		// TODO: Error Handling
		return Integer.parseInt(tfDefense.getText());
	}
	
	public String getRarity() {
		// TODO: Error Handling
		return tfRarity.getText();
	}
	
	public String getName() {
		// TODO: Error Handling
		return tfCardName.getText();
	}
	
	public String getDescription() {
		return taDescription.getText();
	}
	public String getImageName() {
		return imageName;
	}
	
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnChoosePic){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				jFileChooser.addChoosableFileFilter(filter);
				int returnValue = jFileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jFileChooser.getSelectedFile();
					String picName = selectedFile.getName();
					String[] parts = picName.split("\\.");
					imageName = parts[0];
			}
			
		}
		
	}

}
}
