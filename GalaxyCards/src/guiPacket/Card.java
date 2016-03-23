package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;

/**
 * The abstract Superclass Card is responsible for representing a card visually
 * with all its elements. This class sets the standards for how all its children
 * will look like. This class is also responsible for altering its appearance
 * whether the card is in player's hand or on the gameboard.
 * 
 * @author 13120dde
 *
 */
// TODO Change the font to look more appealing and perhaps smaller.
// TODO make the abilitybutton toggle enabled/disabled depending if on hand or
// board.
// TODO enlarge() does not work.
// TODO use ability when button is pressed

public abstract class Card extends JPanel {

	private ImageIcon cardBG1;
//	private Image cardBG;
	private JPanel topPanel, imgPanel, typePanel, attributesPanel;
	private JLabel lbName, lbPrice, lbImage, lbType, lbRarity, lbAttack, lbDefense;
	private Color frameColor;
	private Border border;
	private JButton abilityButton;
	private JTextArea abilityArea;

	private final String PICTURE_DIRECTORY = "files/pictures/";

	public Card() {
		frameColor = Color.BLACK;
		border = BorderFactory.createMatteBorder(1, 1, 3, 1, frameColor);

		cardBG1 = new ImageIcon(PICTURE_DIRECTORY + "CardFrontBG.jpg");
//		setBackground();
		initiateLabels();
		initiateButtons();
		initiatePanels();
		setToolTips();

		this.setBorder(BorderFactory.createLineBorder(frameColor, 3, true));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(topPanel);
		this.add(imgPanel);
		this.add(typePanel);
		this.add(abilityArea);
		this.add(attributesPanel);
	}

	private void setToolTips() {
		lbAttack.setToolTipText("The amount of damage this unit deals.");
		lbDefense.setToolTipText("The amount of defense this unit has.");
		lbPrice.setToolTipText("The cost to play this card.");
	}

	private void initiateButtons() {
		abilityButton = new JButton();
		abilityButton.setIcon(new ImageIcon("files/pictures/ability.jpg"));
		abilityButton.setOpaque(true);
		abilityButton.setPreferredSize(new Dimension(15, 15));
		abilityButton.addActionListener(new AbilityButtonListener());
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
		lbImage.setBorder(BorderFactory.createLoweredBevelBorder());
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

		lbRarity = new JLabel();
		lbRarity.setAlignmentX(SwingConstants.RIGHT);
		lbRarity.setOpaque(true);
		lbRarity.setBorder(border);

		abilityArea = new JTextArea();
		abilityArea.setOpaque(true);
		// abilityArea.setAlignmentX(SwingConstants.LEFT);

		abilityArea.setEditable(false);
		// abilityArea.setWrapStyleWord(true);
		abilityArea.setLineWrap(true);
		abilityArea.setRows(2);
	}

	private void initiatePanels() {

		// Top panel contains the name of the card and the price of the card
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		topPanel.setOpaque(false);
		topPanel.add(lbName);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(lbPrice);

		// Image in for the card
		imgPanel = new JPanel();
		imgPanel.setOpaque(false);
		imgPanel.add(lbImage);

		// Contains the type of card (unit/tech/resource/heroic support) and
		// rarity icon
		typePanel = new JPanel();
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
		typePanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		typePanel.setOpaque(false);
		typePanel.add(lbType);
		typePanel.add(Box.createHorizontalGlue());
		typePanel.add(lbRarity);

		attributesPanel = new JPanel();
		attributesPanel.setLayout(new BoxLayout(attributesPanel, BoxLayout.X_AXIS));
		attributesPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		attributesPanel.setOpaque(false);
		attributesPanel.add(lbAttack);
		attributesPanel.add(Box.createHorizontalGlue());
		attributesPanel.add(abilityButton);
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
		lbImage.setIcon(new ImageIcon(PICTURE_DIRECTORY + imageName + ".jpg"));
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
			lbRarity.setIcon(new ImageIcon(PICTURE_DIRECTORY + "rarityCommon.jpg"));
		} else if (rarity.equalsIgnoreCase("rare")) {
			lbRarity.setIcon(new ImageIcon(PICTURE_DIRECTORY + "rarityRare.jpg"));
		} else if (rarity.equalsIgnoreCase("legendary")) {
			lbRarity.setIcon(new ImageIcon(PICTURE_DIRECTORY + "rarityLegendary.jpg"));
		} else {
			rarity = "unknown rarity, check spelling & whitespace";
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
	 * If true is passed in as argument then the ability button is displayed on
	 * card, else it wont show.
	 * 
	 * @param hasAbility
	 */
	public void hasAbility(boolean hasAbility) {
		abilityButton.setVisible(hasAbility);
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
	 * Sets the description of the ability in the ability panel. Since space is
	 * an issue the String length can be max 55 chars.
	 * 
	 * @param text
	 */
	public void setAbilityText(String text) {
		abilityArea.setText(text);
		abilityButton.setToolTipText(abilityArea.getText());
	}
	
	public String getAbilityText(){
		return abilityArea.getText();
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
			attributesPanel.setVisible(false);
			lbPrice.setVisible(false);
		}
		if (card instanceof HeroicSupport) {
			lbType.setText("Heroic support");
			lbAttack.setVisible(false);
		}
		if (card instanceof Unit) {
			lbType.setText("Unit");
		}
		if (card instanceof Tech) {
			lbType.setText("Tech");
			attributesPanel.setVisible(false);

		}

		// TODO complete with rest of different cardtypes
	}

	// Overridden method that needs to be here to set the background of this
	// JPanel.
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(cardBG1.getImage(), 0, 0, null);
	}

	// Loads a image from a directory and sets it as background for the main
	// containter Card klass.
//	private void setBackground() {
//		File directory = new File(PICTURE_DIRECTORY + "CardFrontBG.jpg");
//		cardBG1 = new ImageIcon(PICTURE_DIRECTORY + "CardFrontBG.jpg");
//		try {
//			cardBG1.setImage(ImageIO.read(directory));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Shows all the card's attributes by setting its various panels visible.
	 * The card should show all it's attributes when its not placed on the
	 * gameboard.
	 * 
	 */
	public void enlarge() {
		topPanel.setVisible(true);
		typePanel.setVisible(true);

		// FUKAR EJ n�r objektet ligger i en JFRame, f�r se om det funkar i en
		// annan container, m�ste kollas upp!
	}

	/**
	 * Hides all unneccesary atributes of the card that are not relevant to the
	 * player when the card is placed on the gameboard by hiding various panels.
	 */
	public void shrink() {
		topPanel.setVisible(false);
		typePanel.setVisible(false);
		abilityArea.setVisible(false);
	}

	private class AbilityButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == abilityButton) {
				JOptionPane.showMessageDialog(null, "WIP! Abilities funktionalitet implementeras vid sprint 2.");

			}

		}

	}
}
