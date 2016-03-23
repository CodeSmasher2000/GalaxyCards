package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Creates a panel that lets you customize a Tech card.
 * @author Jonte
 *
 */

public class CreateTech extends JPanel {
	private JPanel gridTech = new JPanel();

	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNbrOfCards = new JTextField();
	private JTextField tfRarity = new JTextField();
	private JTextArea tfDescription = new JTextArea();
	private JFileChooser jFileChooser = new JFileChooser();
	private JButton btnChoosePic = new JButton("Choose Picture");
	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblRarity = new JLabel("Common/Rare/Legendary");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	private String imageName = null;
	
	/**
	 * adds all the components to the panel and then to the main panel.
	 */
	public CreateTech(){
		gridTech.setLayout(new GridLayout(6,2));
		gridTech.setPreferredSize(new Dimension(400,600));
		gridTech.add(lblCardName);
		gridTech.add(tfCardName);
		gridTech.add(lblPrice);
		gridTech.add(tfPrice);
		gridTech.add(lblRarity);
		gridTech.add(tfRarity);
		gridTech.add(lblDescription);
		gridTech.add(tfDescription);
		gridTech.add(lblNbrOfCards);
		gridTech.add(tfNbrOfCards);
		
		ButtonListener btnListener = new ButtonListener();
		btnChoosePic.addActionListener(btnListener);
		gridTech.add(btnChoosePic);
		
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

	public String getRarity() {
		// TODO HÃ¤mta frÃ¥n Jonatans kod
		return tfRarity.getText();
	}

	public String getImageName() {
		// TODO Auto-generated method stub
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
					String picName = selectedFile.getName(); // man får bara filnamnet.
					String[] parts = picName.split("\\."); // tar bort .jpg från namnet.
					imageName = parts[0];
			}
			
		}
		

	}
	}
}