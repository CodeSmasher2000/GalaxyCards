package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * The abstract Superclass Card is responsible for representing a card visually
 * with all its elements. This class sets the standards for how all its children
 * will look like. This class is also responsible for altering its appearance
 * whether the card is in player's hand or on the gameboard.
 * 
 * @author 13120dde
 *
 */
// TODO *organize the picture directory and update the path string in
// setRarity() setBackground() setImage() methods.
//TODO * create a abilitypanel and add it to the card between type and attributes panels.

public abstract class Card extends JPanel {

	private Image cardBG;
	private JPanel topPanel, imgPanel, typePanel, abilityPanel, attributesPanel;
	private JLabel lbName, lbPrice, lbImage, lbType, lbRarity, lbAttack, lbDefense;
	private Color frameColor;
	private Border border;

	public Card() {
		frameColor = Color.BLACK;
		border = BorderFactory.createMatteBorder(1, 1, 5, 1, frameColor);

		setBackground();
		initiateLabels();
		initiatePanels();

		this.setBorder(BorderFactory.createLineBorder(frameColor, 3, true));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(topPanel);
		this.add(imgPanel);
		this.add(typePanel);
		// TODO need to add a panel that shows attributes of the card, probably
		// need a custom class for that.
		this.add(attributesPanel);
	}

	private void initiateLabels() {

		// Card name
		lbName = new JLabel("  CARD NAME  ");
		lbName.setAlignmentX(SwingConstants.LEFT);
		lbName.setOpaque(true);
		lbName.setBorder(border);

		// Card price
		lbPrice = new JLabel("  3  ");
		lbPrice.setAlignmentX(SwingConstants.RIGHT);
		lbPrice.setOpaque(true);
		lbPrice.setBorder(border);

		// Icon
		lbImage = new JLabel();
		lbImage.setAlignmentX(SwingConstants.CENTER);
		lbImage.setBorder(border);
		lbImage.setIcon(new ImageIcon("files/pictures/test.jpg"));

		// Card type
		lbType = new JLabel("  TYPE  ");
		lbType.setOpaque(true);
		lbType.setBorder(border);

		// Card attack stat
		lbAttack = new JLabel("  3  ");
		lbAttack.setAlignmentX(SwingConstants.LEFT);
		lbAttack.setOpaque(true);
		lbAttack.setBorder(border);

		// Card defense stat
		lbDefense = new JLabel("  3  ");
		lbDefense.setAlignmentX(SwingConstants.RIGHT);
		lbDefense.setOpaque(true);
		lbDefense.setBorder(border);

		// TODO create a set of three icons representing the rarity of the card.
		// Set the rarity icon to this label.
		lbRarity = new JLabel("x");
		lbRarity.setAlignmentX(SwingConstants.RIGHT);
		lbRarity.setOpaque(false);
	}

	private void initiatePanels() {

		// Top panel contains the name of the card and the price of the card
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		topPanel.setOpaque(false);
		topPanel.add(lbName);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(lbPrice);

		// Image in for the card
		imgPanel = new JPanel();
		imgPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		imgPanel.setOpaque(false);
		imgPanel.add(lbImage);

		// Contains the type of card (unit/tech/resource/heroic support) and
		// rarity icon
		typePanel = new JPanel();
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
		typePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		typePanel.setOpaque(false);
		typePanel.add(lbType);

		attributesPanel = new JPanel();
		attributesPanel.setLayout(new BoxLayout(attributesPanel, BoxLayout.X_AXIS));
		attributesPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		attributesPanel.setOpaque(false);
		attributesPanel.add(lbAttack);
		attributesPanel.add(Box.createHorizontalGlue());
		attributesPanel.add(lbRarity);
		attributesPanel.add(Box.createHorizontalGlue());
		attributesPanel.add(lbDefense);

	}

	/**
	 * Method that sets the image of the card. The parameters passed in as
	 * arguments is the name of the picture. The picture need to be in .JPG
	 * format and need to be in the following directory:
	 * GalaxyCards/files/pictures/cardImages/
	 * 
	 * @param imageName
	 */
	public void setImage(String imageName) {
		String path = "files/pictures/cardImages/" + imageName + ".jpg";
		lbImage.setIcon(new ImageIcon(path));
	}

	/**
	 * This method sets the corresponding rarity icon to the rarity label by
	 * evaluating the argument passed in. The argument passed in is a String
	 * where either "common", "rare" or "legendary" sets the corresponding icon.
	 * Not case sensitive.
	 * 
	 * @param rarity
	 *            : String
	 * @return rarity set : String
	 */
	public String setRarity(String rarity) {
		if (rarity.equalsIgnoreCase("common")) {
			lbRarity.setIcon(new ImageIcon("files/pictures/common.jpg"));
		} else if (rarity.equalsIgnoreCase("rare")) {
			lbRarity.setIcon(new ImageIcon("files/pictures/rare.jpg"));
		} else if (rarity.equalsIgnoreCase("legendary")) {
			lbRarity.setIcon(new ImageIcon("files/pictures/legendary.jpg"));
		} else {
			rarity="unknown rarity, check spelling & whitespace";
		}
		return "Rarity set to: " + rarity;
	}

	/**
	 * Sets the name in the name label for this card with the String passed in
	 * as argument.
	 * 
	 */
	public void setName(String name) {
		lbName.setText("  " + name + "  ");
	}

	/**
	 * Sets the price in the price label for this card with an int passed in as
	 * argument.
	 * 
	 * @param price
	 */
	public void setPrice(int price) {
		lbPrice.setText("  " + price + "  ");
	}

	/**
	 * Sets the attack value for this image by passing in an int as argument.
	 * 
	 * @param attack
	 *            : int
	 */
	public void setAttack(int attack) {
		lbAttack.setText("  " + attack + "  ");
	}

	/**
	 * Sets the defense value for this image by passing in an int as argument.
	 * 
	 * @param defense
	 *            : int
	 */
	public void setDefense(int defense) {
		lbDefense.setText("  " + defense + "  ");
	}

	/**
	 * Whenever a new card object is instantiated this method is called upon.
	 * This method checks what type of card the object is and hides irrelevant
	 * elements. For instance if the object is a instance of ResourceCard then
	 * irrelevant elements are price/attack/defense and these elements will be
	 * hidden.
	 * 
	 * @param card
	 */
	public void setType(Card card) {
		if (card instanceof ResourceCard) {
			lbType.setText("Resource");
			lbAttack.setVisible(false);
			lbDefense.setVisible(false);
			lbPrice.setVisible(false);
		}
		if (card instanceof HeroicSupport) {
			lbType.setText("Heroic support");
			lbAttack.setVisible(false);
		}
		if (card instanceof Unit){
			lbType.setText("Unit");
		}

		// TODO complete with rest of different cardtypes
	}

	// Overridden method that needs to be here to set the background of this
	// JPanel.
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(cardBG, 0, 0, null);
	}

	// Loads a image from a directory and sets it as background for the main
	// containter Card klass.
	private void setBackground() {
		File directory = new File("files/pictures/CardFrontBG2.jpg");
		try {
			cardBG = ImageIO.read(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Shows all the card's attributes by setting its various panels visible.
	 * The card should show all it's attributes when its not placed on the
	 * gameboard.
	 * 
	 */
	public void enlarge() {
		topPanel.setVisible(true);
		typePanel.setVisible(true);
		// TODO create a algortim that checks if the abilityPanel is hidden and
		// setVisible if true.
		
		//FUKAR EJ när objektet ligger i en JFRame, får se om det funkar i en annan container, måste kollas upp!
	}

	/**
	 * Hides all unneccesary atributes of the card that are not relevant to the
	 * player when the card is placed on the gameboard by hiding various panels.
	 */
	public void shrink() {
		topPanel.setVisible(false);
		typePanel.setVisible(false);
		// TODO create a algortim that checks the rarity of the card. If the
		// card doesn't contain a special ability then that panel can be hidden
		// too. Else it needs to stay visible.
	}
}
