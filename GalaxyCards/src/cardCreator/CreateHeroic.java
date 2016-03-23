package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Creates a panel that lets you customize a Heroic card.
 * @author Jonte
 *
 */

public class CreateHeroic extends JPanel {
	private JPanel gridHeroic = new JPanel();
	
	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfDefense = new JTextField();
	private JTextField tfRarity = new JTextField("Common/Rare/Legendary");
	private JTextField tfNbrOfCards = new JTextField();
	
	private JCheckBox btnAbility = new JCheckBox();
	private JTextArea taDescription = new JTextArea();
	
	private JFileChooser jFileChooser = new JFileChooser();
	private JButton btnChoosePic = new JButton("Choose Picture");

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblAbility = new JLabel("Ability: Yes/No");
	private JLabel lblDefense = new JLabel("Defense: ");
	private JLabel lblRarity = new JLabel ("Rarity: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	private String imageName = null;
	
	/**
	 * adds all the components to the panel and then to the main panel.
	 */
	public CreateHeroic(){
		gridHeroic.setLayout(new GridLayout(8,2));
		gridHeroic.setPreferredSize(new Dimension(400,600));
		gridHeroic.add(lblCardName);
		gridHeroic.add(tfCardName);
		gridHeroic.add(lblDefense);
		gridHeroic.add(tfDefense);
		gridHeroic.add(lblPrice);
		gridHeroic.add(tfPrice);
		gridHeroic.add(lblRarity);
		gridHeroic.add(tfRarity);
		gridHeroic.add(lblAbility);
		gridHeroic.add(btnAbility);
		gridHeroic.add(lblDescription);
		gridHeroic.add(taDescription);
		gridHeroic.add(lblNbrOfCards);
		gridHeroic.add(tfNbrOfCards);
		
		ButtonListener btnListener = new ButtonListener();
		btnChoosePic.addActionListener(btnListener);
		gridHeroic.add(btnChoosePic);
		
		add(gridHeroic);
	}
	
	public int getPrice() {
		// TODO: Error Handling
		return Integer.parseInt(tfPrice.getText());
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

	public boolean getAbility() {
		btnAbility.isSelected();
		return false;
	}
	
	public void setImageName(String name){
		this.imageName=name;
	}
	public void setName(String name){
		tfCardName.setText(name);
	}
	
	
	public void setDefense(int defense){
		tfDefense.setText(Integer.toString(defense));
	}
	
	public void setRarity(String rarity){
		tfRarity.setText(rarity);
	}
	
	public void setPrice(int price){
		tfPrice.setText(Integer.toString(price));
	}
	
	public void setAbility(boolean ability){
		btnAbility.setSelected(ability);
	}
	
	public void setDescription(String description){
		taDescription.setText(description);
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
					String picName = selectedFile.getName(); // man får bara filnamnet.
					String[] parts = picName.split("\\."); // tar bort .jpg från namnet.
					imageName = parts[0];
			}
			
		}
		

	}
	}
	public int getAmtOfCards() {
		return Integer.parseInt(tfNbrOfCards.getText());
	}
}
