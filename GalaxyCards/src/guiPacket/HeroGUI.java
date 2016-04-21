package guiPacket;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import enumMessage.Persons;

/**
 * Class responsible for visual representation of the hero. The hero has life,
 * energyshield, resources and amount of cards in deck left.
 * 
 * @author 13120dde
 *
 */

// TODO Refactor: Break all comminication with Hero class and add a
// BoadGuiController to constructor. All communication should be managed with
// BoardGuiContorller.

public class HeroGUI extends JPanel {
	private final String PICTURE_DIRECTORY = "files/pictures/";
	private JLabel imageLabel;
	private JPanel imagePanel;
	private JProgressBar lifeBar, shieldBar, resourceBar;
	private ImageIcon heroImage;
	private BoardGuiController boardController;
	private Persons ENUM;

	private Border b1;
	private Border b2;

	public HeroGUI(BoardGuiController boardController, Persons ENUM) {
		this.boardController=boardController;
		this.ENUM=ENUM;
		
		this.boardController.addHeroListener(this, ENUM);
		
		String heroName = boardController.getHeroName();
		b1 = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		b2 = BorderFactory.createTitledBorder(null, heroName, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);

		initiateBars();
		initiateImage();
		setTooltips();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(imagePanel);
		add(resourceBar);
		add(lifeBar);
		add(shieldBar);
	}

	private void setTooltips() {
		lifeBar.setToolTipText("Your hero's life. When it reaches 0 you loose.");
		shieldBar.setToolTipText(
				"Amount of shield left. The shield absorb one instance of damage and the resulting damage will not go trough to the hero's life.");
		resourceBar.setToolTipText(
				"Amount of resources avaible to the hero. Maximum resources increment when playing resource cards and current resources restore upon new round.");
	}

	private void initiateImage() {
		heroImage = new ImageIcon(PICTURE_DIRECTORY + "Hero1" + ".jpg");
		imageLabel = new JLabel();
		imageLabel.setIcon(heroImage);
		imageLabel.setBorder(BorderFactory.createCompoundBorder(b1, b2));

		imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		imagePanel.add(imageLabel);
	}

	private void initiateBars() {

		lifeBar = new JProgressBar();
		lifeBar.setMaximum(20);
		lifeBar.setMinimum(0);
		lifeBar.setForeground(new Color(0, 255, 33));
		lifeBar.setBackground(new Color(0, 127, 14));
		lifeBar.setValue(20);
		lifeBar.setStringPainted(true);
		lifeBar.setString("20 / 20");

		shieldBar = new JProgressBar();
		shieldBar.setMaximum(10);
		shieldBar.setMinimum(0);
		shieldBar.setForeground(new Color(0, 191, 255));
		shieldBar.setBackground(new Color(0, 33, 255));
		shieldBar.setValue(10);
		shieldBar.setStringPainted(true);
		shieldBar.setString("10 / 10");

		resourceBar = new JProgressBar();
		resourceBar.setMaximum(0);
		resourceBar.setMinimum(0);
		resourceBar.setForeground(new Color(198, 124, 255));
		resourceBar.setBackground(new Color(161, 0, 255));
		resourceBar.setValue(0);
		resourceBar.setStringPainted(true);
		resourceBar.setString("0/0");
	}

	/**
	 * Updates the progressbar representing the hero's life with a int given as
	 * argument.
	 * 
	 * @param newValue
	 *            : int
	 */
	public void updateLifeBar(int newValue) {
		lifeBar.setValue(newValue);
		lifeBar.setString(newValue + " / " + "20");
	}

	/**
	 * Updates the progressbar representing the hero's resources with a int
	 * given as argument.
	 * 
	 * @param newValueCurrent
	 *            :int
	 */
	public void updateResourceBar(int newValueCurrent, int newValueMax) {
		resourceBar.setMaximum(newValueMax);
		resourceBar.setValue(newValueCurrent);
		resourceBar.setString(newValueCurrent + " / " + resourceBar.getMaximum());
	}

	/**
	 * Updates the shield progressbar with a int passed in as argument. The
	 * minimum amount of shield represented visually is 0.
	 * 
	 * @param newValue
	 *            : int
	 */
	public void updateShiledBar(int newValue) {
		if (newValue < 0) {
			newValue = 0;
		}
		shieldBar.setValue(newValue);
		shieldBar.setString(newValue + " / " + "10");
	}
}
