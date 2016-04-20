package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import abilities.Ability;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import enumMessage.Lanes;
import enumMessage.Persons;
import exceptionsPacket.GuiContainerException;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.NoLaneSelectedException;
import exceptionsPacket.ResourcePlayedException;
import game.GameController;

/**
 * This class is responsible for message-passing between the gui elements and
 * the Controller object.
 * 
 * @author 13120dde
 *
 */
public class BoardGuiController {

	private GameController gameController;
	
	private HandGUI playerHandGui;
	private OpponentHandGUI opponentHandGui;
	private HeroicPanelGUI heroicGui, opponentHeroicGui;
	private ScrapyardGUI playerScrapyard, opponentScrapyard;
	private HeroGUI playerHeroGui, opponentHeroGui;
	private UnitLanes playerDefLane;
	private UnitLanes playerOffLane;
	private UnitLanes opponentDefLane;
	private UnitLanes opponentOffLane;
	private Persons ENUM;

	private InfoPanelGUI infoPanel;
	private LaneSelectListener laneListener;
	private boolean laneSelected = false;
	private LaneSelectThread laneSelectThread;

	private UnitLanes tempLane;
	private Unit tempUnit;

	// ***MESSAGES SENT TO GUI ELEMENTS************************************
	// *** Methods in this section are called upon from the system to
	// *** update various gui elements.
	// ********************************************************************

	public BoardGuiController(GameController gameController) {
		this.gameController=gameController;
	}
	
	//for debbugging, remove when testpanel is removed.
	public GameController getGameController(){
		return gameController;
	}

	/**
	 * Updates playerHeroGui when changes are made to life, shield or resource
	 * @param life
	 * @param energyShield
	 * @param currentResource
	 */
	public void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		playerHeroGui.updateLifeBar(life);
		playerHeroGui.updateResourceBar(currentResource, maxResource);
		playerHeroGui.updateShiledBar(energyShield);
	}
	
	public void updateOpponentHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		opponentHeroGui.updateLifeBar(life);
		opponentHeroGui.updateResourceBar(currentResource, maxResource);
		opponentHeroGui.updateShiledBar(energyShield);
	
	}
	/**
	 * Attempts to place the Card object passed in as argument to the handGui
	 * container. Throws exception if there is no more space for cards. Maximum
	 * amount of cards on hand = 8.
	 * 
	 * @param card
	 *            : Card
	 * @throws GuiContainerException
	 */
	public void drawCard(Card card) throws GuiContainerException {
		playerHandGui.addCard(card);
	}

	/**
	 * When opponent draws a card the corresponding gui element for opponents
	 * hand get a Icon representing the card's backside added and displayed. The
	 * client does not have referenses to the opponents Card objects on hand.
	 * 
	 * @throws GuiContainerException
	 */
	public void opponentDrawsCard() throws GuiContainerException {
		opponentHandGui.drawCard();
	}

	/**
	 * When opponent plays a unitcard the gui elements get updated with the Unit
	 * object passed in as argument. Lanes.ENUM decide to which unit-panel place
	 * the played card. When PLAYER_DEFENSIVE is passed in as argument the unit
	 * object will be placed in opponent-defensive lane. When PLAYER_OFFENSIVE
	 * is passed in the unit object will be placed in opponents offensive lane.
	 * 
	 * @param unit
	 *            : Unit
	 * @param PLAYER_DEFENSIVE,
	 *            PLAYER_OFFENSIVE :ENUM
	 * @throws GuiContainerException
	 */

	public void opponentPlaysUnit(Unit unit, Lanes ENUM) throws GuiContainerException {
		unit.shrink();
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			opponentDefLane.addUnit(unit);
			opponentHandGui.playCard();
		}
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			opponentOffLane.addUnit(unit);
			opponentHandGui.playCard();
		}
	}

	/**
	 * When opponent plays a heroic support object the heroicSupportGUI panel
	 * for opponents objects get updated with the argument passed in.
	 * 
	 * @param heroicSupport
	 *            : HeroicSupport
	 * @throws GuiContainerException
	 */
	public void opponentPlaysHeroicSupport(HeroicSupport heroicSupport) throws GuiContainerException {
		opponentHeroicGui.addHeroicSupport(heroicSupport);
		opponentHandGui.playCard();
	}

	/**
	 * Moves the Card object to the opponent's scrapyard container.
	 * 
	 * @param card
	 */
	public void addToOpponentScrapyard(Card card) {
		opponentScrapyard.addCard(cloneCard(card));
	}

	public void opponentPlaysTech(Tech tech) {
		// TODO
	}
	
	/**
	 * Updates the opponents Resource-pool when a resource is played.
	 * @param resourceCard
	 */
	public void opponentPlaysResource(ResourceCard resourceCard,int newValue, int maxValue) throws GuiContainerException{
		opponentHeroGui.updateResourceBar(newValue,maxValue);
		addToOpponentScrapyard(resourceCard);
		opponentHandGui.playCard();
	}

	public void opponentPlaysAbility(Ability ability) {
		// TODO
	}

	// ***PROTECTED METHODs*****************************************************
	// *** Methods called upon from this package from various gui-elements
	// *************************************************************************

	/**
	 * Sets up a association between this object and the ScrapYardGUI object. A
	 * Persons enum is passed in to detrminate if the object belongs to the
	 * player or opponent.
	 * 
	 * @param hand
	 *            : ScrapyardGUI
	 * @param PLAYER,
	 *            OPPONENT : Persons
	 */
	protected void addScrapyardListener(ScrapyardGUI scrapyardGUI, Persons ENUM) {
		if (ENUM == Persons.PLAYER) {
			playerScrapyard = scrapyardGUI;
		}
		if (ENUM == Persons.OPPONENT) {
			opponentScrapyard = scrapyardGUI;
		}
	}

	/**
	 * Sets up a association between this object and the HandGUI object.
	 * 
	 * @param hand
	 */
	protected void addHandPanelListener(HandGUI hand) {
		playerHandGui = hand;
	}

	/**
	 * Sets up a association between this object and the opponent's HandGUI
	 * object.
	 * 
	 * @param hand
	 */
	protected void addOpponentHandListener(OpponentHandGUI hand) {
		opponentHandGui = hand;
	}

	/**
	 * Sets up a association between this object and the HoveredCardUI
	 * 
	 * @param hoveredCard
	 */
	protected void addInfoPanelListener(InfoPanelGUI infoPanel) {
		this.infoPanel = infoPanel;
	}

	/**
	 * Sets up a association between this object the heroicPanelGUI objects.
	 * ENUM is passed in as argument to set up different versions of the class
	 * depending if the object belong to the player or the opponent.
	 * 
	 * @param heroicPanelGUI
	 * @param ENUM
	 *            : PLAYER, OPPONENT
	 */
	protected void addHeroicPanelListener(HeroicPanelGUI heroicPanelGUI, Persons ENUM) {
		if (ENUM == Persons.PLAYER) {
			heroicGui = heroicPanelGUI;
		}
		if (ENUM == Persons.OPPONENT) {
			opponentHeroicGui = heroicPanelGUI;
		}
	}

	/**
	 * Sets up a association between this object and the HeroGUI object.
	 * 
	 * @param heroGui
	 */
	protected void addHeroListener(HeroGUI heroGui) {
		this.playerHeroGui = heroGui;
	}

	/**
	 * Sets up a association between this object and the ArrayLayeredPane
	 * objects. A ENUM is passed in as argument to ensure that there are 4
	 * different that corresponds offensive / defensive lanes for player and
	 * opponent.
	 * 
	 * @param arrayLayeredPane
	 * @param ENUM
	 *            : PLAYER_OFFENSIVE, PLAYER_DEFENSIVE, ENEMY_OFFENSIVE,
	 *            ENEMY_DEFENSIVE
	 */
	protected void addLaneListener(UnitLanes arrayLayeredPane, Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			opponentDefLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			opponentOffLane = arrayLayeredPane;
		}

	}

	// ***MESSAGES SENT TO THE SYSTEM**************************************
	// *** Methods in this section are called upon from the GUI elements***
	// *** to update various gui elements. ***
	// ********************************************************************

	protected void addCardToHand(Card card) {
		// controller.addCardToHand(card);
	}

	protected void clearHand() {
		// controller.clearHand();
	}

	protected void getCardType(Card playCard) {
		// TODO Auto-generated method stub

	}

	protected String getHeroName() {
		// TODO Auto-generated method stub
		// TODO get the hero name from the Hero class.
		return null;
	}

	// ***MESSAGES SENT BETWEEN GUIELEMENTS************************************
	// *** Methods in this section are called upon from the GUI elements ***
	// *** to update various gui elements. ***
	// ************************************************************************
	/**
	 * Method that checks the type of card played and places it in the suggested
	 * container on BoardGUI. This method is called upon when the user selects
	 * the card Object from HandGUI panel and attempts to play it.
	 * 
	 * @param card
	 * @throws GuiContainerException
	 * @throws NoLaneSelectedException
	 * @throws ResourcePlayedException 
	 * @throws InsufficientResourcesException 
	 */
	protected void playCard(Card card) throws GuiContainerException, NoLaneSelectedException, ResourcePlayedException, InsufficientResourcesException {
		if (card instanceof ResourceCard) {
			ResourceCard temp = (ResourceCard) card;
			playResourceCard((ResourceCard)cloneCard(temp));
		}
		// Do not use the cloneCard() method when playing unitCard... need a
		// referense to the original object while waiting for lane-selection
		// input.
		if (card instanceof Unit) {
			tempUnit = (Unit) card;
			startSelectLaneThread();
		}
		if (card instanceof HeroicSupport) {
			HeroicSupport temp = (HeroicSupport) card;
			playHeroicSupport((HeroicSupport) cloneCard(temp));
		}
		if (card instanceof Tech) {
			Tech temp = (Tech) card;
			playTech((Tech) cloneCard(temp));

		}
		card = null;
	}

	/**
	 * Whenever a cardobject moves between different containers it needs to be
	 * cloned or else the visuals will hang up the system.
	 * 
	 * @param card
	 *            : Card
	 * @return card : Card
	 */
	protected Card cloneCard(Card card) {
		if (card instanceof ResourceCard) {
			ResourceCard clonedCard = new ResourceCard();
			return clonedCard;
		} else if (card instanceof HeroicSupport) {
			HeroicSupport temp = (HeroicSupport) card;
			HeroicSupport clonedCard = new HeroicSupport(temp.getName(), temp.getRarity(), temp.getImage(),
					temp.hasAbility(), temp.getPrice(), temp.getDefense());
			temp = null;
			return clonedCard;
		} else if (card instanceof Tech) {
			Tech temp = (Tech) card;
			Tech clonedCard = new Tech(temp.getName(), temp.getRarity(), temp.getImage(), temp.getPrice());
			return clonedCard;
		} else {
			Unit temp = (Unit) card;
			Unit clonedCard = new Unit(temp.getName(), temp.getRarity(), temp.getImage(), temp.hasAbility(),
					temp.getAttack(), temp.getDefense(), temp.getPrice());
			card = null;
			return clonedCard;
		}
	}

	/**
	 * Moves the Card object to the PlayerScrapyard container
	 * 
	 * @param card
	 *            : Card
	 */
	protected void addToPlayerScrapyard(Card card) {
		playerScrapyard.addCard(cloneCard(card));
	}

	/**
	 * UnitCards that are placed in UnitLanes container get the mouseover
	 * ability to show the object in its full size on the infoPanel.
	 * 
	 * @param cardToShow
	 */
	protected void updateInfoPanelCard(Card cardToShow) {
		cardToShow = (Card) cloneCard(cardToShow);
		infoPanel.showCard(cardToShow);
	}
	
	protected int getAvaibleResources() {
		return gameController.getAvaibleResources();
	}

	// ***PRIVATE METHODS******************************************************
	// *** Methods in this section are called within this class
	// ************************************************************************

	private void playResourceCard(ResourceCard card) throws ResourcePlayedException {
		gameController.playResourceCard(card);
		playerScrapyard.addCard(card);
	}

	private void playHeroicSupport(HeroicSupport cardToPlay) throws GuiContainerException, InsufficientResourcesException {
		gameController.playHeroicSupport(cardToPlay);
		heroicGui.addHeroicSupport(cardToPlay);
	}

	private void playUnitCard(Unit cardToPlay) throws GuiContainerException, InsufficientResourcesException{
		cardToPlay = (Unit) cloneCard(cardToPlay);
		System.out.println(Thread.currentThread());
		gameController.playUnit(cardToPlay, tempLane.getLaneType());
		cardToPlay.shrink();
		if (tempLane.getLaneType() == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane.addUnit(cardToPlay);
		}
		if (tempLane.getLaneType() == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane.addUnit(cardToPlay);
		}
	}

	private void playTech(Tech cloneCard) {
		// TODO Figure out how to use abilities before implementing more to this
		// method.
	}

	private void setSelectedLane() throws GuiContainerException, InsufficientResourcesException, ResourcePlayedException {
		playUnitCard(tempUnit);
		playerHandGui.playCard(tempUnit);
		laneSelected = true;
	}

	/**
	 * When the player attempts to play a unit card he need to select which lane
	 * to place the unit on (defensive or offensive). Start a new thread that
	 * waits for player's input. Input is done by clicking on the lane player
	 * want to place units on.
	 * 
	 * @throws NoLaneSelectedException
	 */
	private void startSelectLaneThread() throws NoLaneSelectedException {
		if (laneSelectThread == null) {
			laneSelectThread = new LaneSelectThread();
			laneSelectThread.start();
		}

		if (!laneSelected) {
			throw new NoLaneSelectedException("Thread for selecting lane started...waiting for input");
		}

	}

	/**
	 * A thread that waits for player input when selecting a lane to play unit
	 * cards on. Instantiates a mouselistener that gets connected to the
	 * lanePanels that unit objects can be placed in.
	 * 
	 * @author 13120dde
	 *
	 */
	private class LaneSelectThread extends Thread {

		public LaneSelectThread() {
			InfoPanelGUI.append("Lane select thread started...waiting for unput");
			laneListener = new LaneSelectListener();
			playerOffLane.addMouseListener(laneListener);
			playerDefLane.addMouseListener(laneListener);

			playerOffLane.setBorder(CustomGui.offLaneBorder);
			playerDefLane.setBorder(CustomGui.deffLaneBorder);

			playerDefLane.setBackground(CustomGui.guiTransparentColor);
			playerOffLane.setBackground(CustomGui.guiTransparentColor);

		}

		public void run() {
			while (!laneSelected || Thread.interrupted()) {
				System.err.println("Waiting for selection of lane");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// When the input is done remove interaction from the lane panels.
			playerOffLane.removeMouseListener(laneListener);
			playerDefLane.removeMouseListener(laneListener);

			playerOffLane.setBorder(null);
			playerDefLane.setBorder(null);

			playerOffLane.setOpaque(false);
			playerDefLane.setOpaque(false);

			laneListener = null;
			laneSelected = false;
			laneSelectThread = null;
			InfoPanelGUI.append("Lane select thread stopped");

		}
	}

	private class LaneSelectListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent event) {
			if (event.getSource() == playerDefLane) {
				playerDefLane.setBorder(CustomGui.defLaneMarkedBorder);
				playerDefLane.setOpaque(true);
			}
			if (event.getSource() == playerOffLane) {
				playerOffLane.setBorder(CustomGui.offLaneMarkedBorder);
				playerOffLane.setOpaque(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent event) {
			if (event.getSource() == playerDefLane) {
				playerDefLane.setBorder(CustomGui.deffLaneBorder);
				playerDefLane.setOpaque(false);
			}
			if (event.getSource() == playerOffLane) {
				playerOffLane.setBorder(CustomGui.offLaneBorder);
				playerOffLane.setOpaque(false);
			}
		}

		@Override
		public void mousePressed(MouseEvent event) {
			tempLane = (UnitLanes) event.getSource();
			try {
				setSelectedLane();
			} catch (GuiContainerException e) {
				// TODO Auto-generated catch block
				InfoPanelGUI.append(e.getMessage());
				laneSelected = true;
			} catch (InsufficientResourcesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourcePlayedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Do nothing
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

	}
}
