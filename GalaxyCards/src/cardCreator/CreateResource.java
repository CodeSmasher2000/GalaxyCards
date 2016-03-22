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
 * Creates a panel which lets you customize a Resource card.
 * @author Jonte
 *
 */

public class CreateResource extends JPanel {
	private JPanel gridResource = new JPanel();
	
	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNbrOfCards = new JTextField();
	private JTextArea tfDescription = new JTextArea();
	
	private JFileChooser jFileChooser = new JFileChooser();
	private JButton btnChoosePic = new JButton("Choose Picture");

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	private String imageName = null;
	
	/**
	 * adds all the components to the panel and then to the main panel.
	 */
	public CreateResource(){
		gridResource.setLayout(new GridLayout(5,2));
		gridResource.setPreferredSize(new Dimension(400,600));
		gridResource.add(lblCardName);
		gridResource.add(tfCardName);
		gridResource.add(lblPrice);
		gridResource.add(tfPrice);
		gridResource.add(lblDescription);
		gridResource.add(tfDescription);
		gridResource.add(lblNbrOfCards);
		gridResource.add(tfNbrOfCards);
		
		ButtonListener btnListener = new ButtonListener();
		btnChoosePic.addActionListener(btnListener);
		gridResource.add(btnChoosePic);
		
		add(gridResource);
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
	
	public String getImageName(){
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

	public int getAmtOfCards() {
		return Integer.parseInt(tfNbrOfCards.getText());
	}
}
