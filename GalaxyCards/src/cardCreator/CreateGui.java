package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import guiPacket.Card;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;

/**
 * Sets up the gui components and creates the Controller.
 * @author Jonte
 *
 */
public class CreateGui extends JPanel {
	private JTabbedPane tabs = new JTabbedPane();
	private JPanel mainPanel = new JPanel();
	
	// Specilized Panels 
	private CreateUnit createUnit = new CreateUnit();
	private CreateResource createResource = new CreateResource();
	private CreateTech createTech = new CreateTech();
	private CreateHeroic createHeroic = new CreateHeroic();
	private CardPane previewPanel;
	private DeckPanel deckPanel;
	
	private CreateController controller;
	
	public CreateGui(CreateController controller){
		this.controller = controller;
		JFrame frame1 = new JFrame("Card Creator");
		frame1.setPreferredSize(new Dimension(1300,700));
		frame1.setLayout(new GridLayout(1,3));
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		previewPanel = new CardPane(controller, this);
		deckPanel = new DeckPanel(controller);
		tabs.addTab("Unit", createUnit);
		tabs.addTab("Resource", createResource);
		tabs.addTab("Tech", createTech);
		tabs.addTab("Heroic", createHeroic);
		frame1.add(deckPanel);
		frame1.add(tabs);
		frame1.add(previewPanel);
		frame1.pack();
		
	}
	
	public CreateController getController() {
		return this.controller;
	}
	
	/**
	 * Adds the selected card with the specified values.
	 */
	public void addCard() {
		if (tabs.getSelectedComponent().equals(createUnit)) {
			System.out.println("Create Unit");
			controller.addUnitCard(createUnit.getName(), createUnit.getRarity(), createUnit.getImageName(), 
					createUnit.getAttack(), createUnit.getDefense(), createUnit.getPrice(), createUnit.getDescription());
		} else if (tabs.getSelectedComponent().equals(createHeroic)) {
			System.out.println("Create Heroic");
			controller.addHeroicSupportCard(createHeroic.getName(), createHeroic.getRarity(), createHeroic.getImageName(),
					createHeroic.getPrice(), createHeroic.getDefense(),createHeroic.getDescription(),createHeroic.getAbility());
		} else if (tabs.getSelectedComponent().equals(createResource)) {
			System.out.println("Create Resource");
			controller.addResoruceCard(createResource.getAmtOfCards());
		} else if(tabs.getSelectedComponent().equals(createTech)) {
			System.out.println("Create Tech");
			controller.addTechCard(createTech.getName(), createTech.getRarity(), createTech.getImageName(), createTech.getPrice(),
					createTech.getDescription(),createTech.getAbility());
		}
	}
	/**
	 * Removes a card from the deck.
	 * @param card
	 * 			The card to be removed.
	 */
	public void removeCardFromList(Card card) {
		deckPanel.removeFromList(card);
	}
	
	/**
	 * Updates the preview panel with the selected card.
	 */
	public void updateCardPreview() {
		if (tabs.getSelectedComponent().equals(createUnit)) {
			System.out.println("Preview Unit");
			Unit card = new Unit(createUnit.getName(), createUnit.getRarity(),createUnit.getImageName(),
					createUnit.getAttack(), createUnit.getDefense(),createUnit.getPrice());
			card.setAbilityText(createUnit.getDescription());
			previewPanel.setCard(card);
		} else if (tabs.getSelectedComponent().equals(createHeroic)) {
			System.out.println("Preview Heroic");
			HeroicSupport card = new HeroicSupport(createHeroic.getName(), createHeroic.getRarity(), createHeroic.getImageName()
					, createHeroic.getPrice(), createHeroic.getDefense(),createHeroic.getAbility());
			card.setAbilityText(createHeroic.getDescription());
			previewPanel.setCard(card);
		} else if (tabs.getSelectedComponent().equals(createResource)) {
			System.out.println("Preview Resource");
			ResourceCard card = new ResourceCard();
			previewPanel.setCard(card);
		} else if(tabs.getSelectedComponent().equals(createTech)) {
			System.out.println("Preview Tech");
			Tech card = new Tech(createTech.getName(), createTech.getRarity(), createTech.getImageName(), createTech.getPrice(),
					createTech.getAbility());
			card.setAbilityText(createTech.getDescription());
			previewPanel.setCard(card);
		}
	}
	
	/**
	 * Updates the textfields and textareas with the selected unit card.
	 * @param card
	 * 			The card to update the fields with.
	 */
	public void updateUnitFields(Unit card){
		tabs.setSelectedComponent(createUnit);
		createUnit.setName(card.getName());
		createUnit.setPrice(card.getPrice());
		createUnit.setDefense(card.getDefense());
		createUnit.setAttack(card.getAttack());
		createUnit.setRarity(card.getRarity());
		createUnit.setDescription(card.getAbilityText());
		createUnit.setImageName(card.getImage());
	}
	
	/**
	 * Updates the textfields and textareas with the selected Tech card.
	 * @param card
	 * 			The card to update the fields with.
	 */
	public void updateTechFields(Tech card){
		tabs.setSelectedComponent(createTech);
		createTech.setName(card.getName());
		createTech.setPrice(card.getPrice());
		createTech.setRarity(card.getRarity());
		createTech.setDescription(card.getAbilityText());
		createTech.setImageName(card.getImage());
	}
	
	/**
	 * Updates the textfields and textareas with the selected Reource card.
	 * @param card
	 * 			The card to update the fields with.
	 */
	public void updateResourceFields(ResourceCard card){
		tabs.setSelectedComponent(createResource);
	}
	
	/**
	 * Updates the textfields and textareas with the selected Heroic-support card.
	 * @param card
	 * 			The card to update the fields with.
	 */
	public void updateHeroicFields(HeroicSupport card){
		tabs.setSelectedComponent(createHeroic);
		createHeroic.setName(card.getName());
		createHeroic.setPrice(card.getPrice());
		createHeroic.setDefense(card.getDefense());
		createHeroic.setRarity(card.getRarity());
		createHeroic.setDescription(card.getAbilityText());
		createHeroic.setImageName(card.getImage());
	}
	
	/**
	 * Adds selected card to the List model.
	 * @param cardToAdd
	 * 			The card to add.
	 */
	public void addCardToList(Card cardToAdd) {
		deckPanel.addToListModel(cardToAdd);
	}
	
	public static void main(String[] args) {
		new CreateGui(new CreateController());
	}

}
