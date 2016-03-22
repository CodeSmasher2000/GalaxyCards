package cardCreator;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import guiPacket.Card;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;

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
		frame1.setLayout(new GridLayout(1,3));
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		previewPanel = new CardPane(controller, this);
		deckPanel = new DeckPanel();
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
	
	public void addCard() {
		if (tabs.getSelectedComponent().equals(createUnit)) {
			System.out.println("Create Unit");
			controller.addUnitCard(createUnit.getName(), createUnit.getRarity(), createUnit.getImageName(), createUnit.getAbility(),
					createUnit.getAttack(), createUnit.getDefense(), createUnit.getPrice());
		} else if (tabs.getSelectedComponent().equals(createHeroic)) {
			System.out.println("Create Heroic");
		} else if (tabs.getSelectedComponent().equals(createResource)) {
			System.out.println("Create Resource");
		} else if(tabs.getSelectedComponent().equals(createTech)) {
			System.out.println("Create Tech");
		}
	}
	
	public void removeCardFromList() {
	}
	
	public void updateCardPreview() {
		if (tabs.getSelectedComponent().equals(createUnit)) {
			System.out.println("Preview Unit");
			Unit card = new Unit(createUnit.getName(), createUnit.getRarity(),createUnit.getImageName(),createUnit.getAbility(),
					createUnit.getAttack(), createUnit.getDefense(),createUnit.getPrice());
			card.setAbilityText(createUnit.getDescription());
			previewPanel.setCard(card);
		} else if (tabs.getSelectedComponent().equals(createHeroic)) {
			System.out.println("Preview Heroic");
			HeroicSupport card = new HeroicSupport(createHeroic.getName(), createHeroic.getRarity(), createHeroic.getImageName(),
					createHeroic.getAbility(), createHeroic.getPrice(), createHeroic.getDefense());
			previewPanel.setCard(card);
		} else if (tabs.getSelectedComponent().equals(createResource)) {
			System.out.println("Preview Resource");
			ResourceCard card = new ResourceCard();
			card.setAbilityText(createResource.getDescription());
			previewPanel.setCard(card);
		} else if(tabs.getSelectedComponent().equals(createTech)) {
			System.out.println("Preview Tech");
			Tech card = new Tech(createTech.getName(), createTech.gerRarity(), createTech.getImageName(), createTech.getPrice());
			previewPanel.setCard(card);
		}
	}
	
	public void addCardToList(Card cardToAdd) {
		deckPanel.addToListModel(cardToAdd);
	}
	
	public static void main(String[] args) {
		new CreateGui(new CreateController());
	}

}
