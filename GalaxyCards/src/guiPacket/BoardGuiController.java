package guiPacket;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import abilities.Ability;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Target;
import cards.Tech;
import cards.Unit;
import enumMessage.Lanes;
import enumMessage.Persons;
import enumMessage.Phase;
import exceptionsPacket.GuiContainerException;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.NoLaneSelectedException;
import exceptionsPacket.ResourcePlayedException;
import game.GameController;
import move.Attack;

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
	private HeroicPanelGUI playerHeroicGui, opponentHeroicGui;
	private ScrapyardGUI playerScrapyard, opponentScrapyard;
	private HeroGUI playerHeroGui, opponentHeroGui;
	private UnitLanes playerDefLane;
	private UnitLanes playerOffLane;
	private UnitLanes opponentDefLane;
	private UnitLanes opponentOffLane;
	private Persons ENUM;

	private InfoPanelGUI infoPanel;

	private LaneSelectListener laneListener;
	private LaneSelectThread laneSelectThread;
	private AttackThreadListener attackSelectThread;
	private DefendThreadListener defendSelectThread;
	private AbilityThreadListener abilitySelectThread;

	private boolean targetSelected = false;
	private boolean defendingTargetSelected = false;
	private boolean laneSelected = false;
	private boolean abilityTargetSelected = false;

	private UnitLanes tempLane;
	private Unit tempUnit;

	// ***MESSAGES SENT TO GUI ELEMENTS************************************
	// *** Methods in this section are called upon from the system to
	// *** update various gui elements.
	// ********************************************************************

	public BoardGuiController(GameController gameController) {
		this.gameController = gameController;
	}

	// for debbugging, remove when testpanel is removed.
	public GameController getGameController() {
		return gameController;
	}

	public void checkStatus() {
		opponentDefLane.checkStatus();
		opponentOffLane.checkStatus();
		playerDefLane.checkStatus();
		playerOffLane.checkStatus();
		opponentHeroicGui.checkStatus();
		playerHeroicGui.checkStatus();
	}

	/**
	 * Updates playerHeroGui when changes are made to life, shield or resource
	 * 
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

	public void addToPlayerScrapYard(Card card) {
		// TODO Ska förmodligen ändras
		playerScrapyard.addCard(cloneCard(card));
	}

	/**
	 * Updates the opponents Resource-pool when a resource is played.
	 * 
	 * @param resourceCard
	 */
	public void opponentPlaysResource(ResourceCard resourceCard) throws GuiContainerException {
		// addToOpponentScrapyard(resourceCard);
		opponentHandGui.playCard();
	}

	public void opponentPlaysAbility() {
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
			playerHeroicGui = heroicPanelGUI;
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
	protected void addHeroListener(HeroGUI heroGui, Persons ENUM) {
		if (ENUM == Persons.PLAYER) {
			this.playerHeroGui = heroGui;
		} else {
			this.opponentHeroGui = heroGui;
		}
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
	public void playCard(Card card) throws GuiContainerException, NoLaneSelectedException, ResourcePlayedException,
			InsufficientResourcesException {
		if (card instanceof ResourceCard) {
			ResourceCard temp = (ResourceCard) card;
			playResourceCard((ResourceCard) cloneCard(temp));
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
			clonedCard.setId(card.getId());
			return clonedCard;
		} else if (card instanceof HeroicSupport) {
			HeroicSupport temp = (HeroicSupport) card;
			HeroicSupport clonedCard = new HeroicSupport(temp.getName(), temp.getRarity(), temp.getImage(),
					 temp.getPrice(), temp.getDefense(), temp.getAbility());
			clonedCard.setId(card.getId());
			clonedCard.setLanesEnum(temp.getLaneEnum());
			clonedCard.setAbilityText(temp.getAbilityText());
			temp = null;
			return clonedCard;
		} else if (card instanceof Tech) {
			Tech temp = (Tech) card;
			Tech clonedCard = new Tech(temp.getName(), temp.getRarity(), temp.getImage(), temp.getPrice(),
					temp.getAbility());
			clonedCard.setId(card.getId());
			clonedCard.setAbilityText(temp.getAbilityText());
			return clonedCard;
		} else if (card instanceof Unit) {
			Unit temp = (Unit) card;
			Unit clonedCard = new Unit(temp.getName(), temp.getRarity(), temp.getImage(), temp.getAttack(),
					temp.getDefense(), temp.getPrice());
			clonedCard.setId(card.getId());
			clonedCard.setLaneEnum(temp.getLaneEnum());
			clonedCard.setAbilityText(temp.getAbilityText());
			card = null;
			temp = null;
			return clonedCard;
		} else {
			return null;
		}
	}

	/**
	 * Moves the Card object to the PlayerScrapyard container
	 * 
	 * @param card
	 *            : Card
	 */
	protected void addToPlayerScrapyard(Card card) {
		// gameController.updateOpponentScrapYard(card);
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

	// ***PRIVATE METHODS******************************************************
	// *** Methods in this section are called within this class
	// ************************************************************************

	private void playResourceCard(ResourceCard card) throws ResourcePlayedException {
		gameController.playResourceCard(card);
	}

	public void removeCardFromHand(Card card) {
		playerHandGui.removeCard(card);
	}

	private void playHeroicSupport(HeroicSupport cardToPlay)
			throws GuiContainerException, InsufficientResourcesException {
		gameController.playHeroicSupport(cardToPlay);
		// playerHeroicGui.addHeroicSupport(cardToPlay);
	}

	/**
	 * Metoden används sålänge för att innehålla den kod som behövs för att
	 * lägga till ett kort från gc.
	 * 
	 * @param toAdd
	 */
	public void addHeroicSupport(HeroicSupport toAdd) {
		try {
			playerHeroicGui.addHeroicSupport(toAdd);
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playUnitCard(Unit cardToPlay) throws GuiContainerException {
		gameController.playUnit(cardToPlay, tempLane.getLaneType());
	}

	public void addUnitCard(Unit UnitCardToAdd, Lanes lane) {
		Unit card = (Unit) cloneCard(UnitCardToAdd);
		card.shrink();
		try {
			if (lane == Lanes.PLAYER_DEFENSIVE) {
				playerDefLane.addUnit(card);
			} else if (lane == Lanes.PLAYER_OFFENSIVE) {
				playerOffLane.addUnit(card);
			} else if (lane == Lanes.ENEMY_DEFENSIVE) {
				opponentDefLane.addUnit(card);
			} else if (lane == Lanes.ENEMY_OFFENSIVE) {
				opponentOffLane.addUnit(card);
			}
		} catch (GuiContainerException e) {
			e.printStackTrace();
		}
	}

	private void playTech(Tech cloneCard) {
		try {
			gameController.playTech(cloneCard);
		} catch (InsufficientResourcesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setSelectedLane()
			throws GuiContainerException, InsufficientResourcesException, ResourcePlayedException {

		laneSelected = true;
		playUnitCard(tempUnit);
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

	public void startAttackThreadListner() {
		if (attackSelectThread == null) {
			attackSelectThread = new AttackThreadListener();
			attackSelectThread.start();
		}
	}

	protected boolean getAttackThreadStarted() {
		if (attackSelectThread == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void startAbilityThreadListener(Card cardWithAbility){
		if(abilitySelectThread == null){
			abilitySelectThread = new AbilityThreadListener(cardWithAbility);
			abilitySelectThread.start();
		}
	}
	
	protected boolean getAbilityThreadStarted(){
		if(abilitySelectThread == null){
			return false;
		}else{
			return true;
		}
	}

	public void startDefendThreadListener(Card card) {
		setDefendingTargetSelected(false);
		if (defendSelectThread == null) {
			defendSelectThread = new DefendThreadListener((Unit) card);
			defendSelectThread.start();
		}
	}

	protected boolean getDefendThreadStarted() {
		if (defendSelectThread == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isTargetSelected() {
		return targetSelected;
	}

	public void setTargetSelected(boolean targetSelected) {
		this.targetSelected = targetSelected;
	}

	public void setFriendlyHeroId(int id) {
		this.playerHeroGui.setId(id);
	}

	public void setEnemyHeroId(int id) {
		this.opponentHeroGui.setId(id);
	}

	/**
	 * A thread that waits for player input when selecting a lane to play unit
	 * cards on. Instantiates a mouselistener that gets connected to the
	 * lanePanels that unit objects can be placed in.
	 * 
	 * @author 13120dde
	 *
	 */


	public void setAttacker(Card card) {
		attackSelectThread.setAttacker(card);
	}

	public void setDefender(Object defender) {
		attackSelectThread.setDefender(defender);
	}

	public void changeAttackersTarget(Unit attacker) {
		defendSelectThread.changeAttackersTarget(attacker);
	}
	
	public void setAbilityTarget(int targetId, Lanes targetLane) {
		abilitySelectThread.setAbilityTarget(targetId, targetLane);
		
	}

	public void tapCard(int cardId, Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane.tapCard(cardId);
		}
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane.tapCard(cardId);
		}
		if (ENUM == Lanes.PLAYER_HEROIC) {
			playerHeroicGui.tapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			opponentOffLane.tapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			opponentDefLane.tapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_HEROIC) {
			opponentHeroicGui.tapCard(cardId);
		}
	}

	public void untapCard(int cardId, Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane.untapCard(cardId);
		}
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane.untapCard(cardId);
		}
		if (ENUM == Lanes.PLAYER_HEROIC) {
			playerHeroicGui.untapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			opponentOffLane.untapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			opponentDefLane.untapCard(cardId);
		}
		if (ENUM == Lanes.ENEMY_HEROIC) {
			opponentHeroicGui.untapCard(cardId);
		}

	}

	/**
	 * Taps all cards in the specified lane passed in as argument.
	 * 
	 * @param ENUM
	 *            : Lanes
	 */
	public void tapAllInLane(Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane.tapAllInLane();
		}
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane.tapAllInLane();
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			opponentOffLane.tapAllInLane();
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			opponentDefLane.tapAllInLane();
		}
	}

	/**
	 * Untaps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM
	 *            : Lanes
	 */
	public void untapAllInLane(Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane.untapAllInLane();
		}
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane.untapAllInLane();
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			opponentOffLane.untapAllInLane();
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			opponentDefLane.untapAllInLane();
		}
		if (ENUM == Lanes.PLAYER_HEROIC) {
			playerHeroicGui.untapAllInLane();
		}
		if (ENUM == Lanes.ENEMY_HEROIC) {
			opponentHeroicGui.untapAllInLane();
		}
	}

	public int getFriendlyHeroId() {
		return playerHeroGui.getId();
	}

	public int getOpponetHeroId() {
		return opponentHeroGui.getId();
	}

	public boolean getDefendingTargetSelected() {
		return defendingTargetSelected;
	}

	public void setDefendingTargetSelected(boolean defendingTargetSelected) {
		this.defendingTargetSelected = defendingTargetSelected;
	}
	
	public boolean getAbilityTargetSelected(){
		return abilityTargetSelected;
	}
	
	public void setAbilityTargetSelected(boolean abilityTarget){
		abilityTargetSelected=abilityTarget;
	}

	public Phase getPhase() {
		return gameController.getPhase();
	}

	public Attack getAttackObject() {
		return gameController.getAttack();
	}
	public void updateCard(Card cardToUpdate) {
		// TODO : FIX THE UNIT CARD METHOD

		if (cardToUpdate instanceof Unit) {
			if (playerDefLane.updateCard((Unit) cardToUpdate)) {
				return;
			}
			if (playerOffLane.updateCard((Unit) cardToUpdate)) {
				return;
			}
			if (opponentDefLane.updateCard((Unit) cardToUpdate)) {
				return;
			}
			if (opponentOffLane.updateCard((Unit) cardToUpdate)) {
				return;
			}
		}

		if (cardToUpdate instanceof HeroicSupport) {
			if (opponentHeroicGui.updateCard((HeroicSupport) cardToUpdate)) {
				return;
			}
			if (playerHeroicGui.updateCard((HeroicSupport) cardToUpdate)) {
				return;
			}
		}
		InfoPanelGUI.append("Update Card : Something went wrong");
	}

	/**
	 * This method checks what kind of card is passed in as argument (Tech or
	 * heroic support) and checks if the ability needs a target. If the ability
	 * needs a target then a AbilitThreadListener thread will start which will set a
	 * target upon mouseinteraction. If the card is HeroicSupport it will get
	 * tapped when the ability is used. Finnaly the card will be sent to the server.
	 * 
	 * @param cardWithAbility
	 *            : Card
	 */
	public void useAbility(Card cardWithAbility) {
		
		if(cardWithAbility instanceof HeroicSupport){
			if(((HeroicSupport)cardWithAbility).getAbility().hasTarget()){
				startAbilityThreadListener(cardWithAbility);
			}else{
				tapCard(((HeroicSupport) cardWithAbility).getId(), ((HeroicSupport) cardWithAbility).getLaneEnum());
				gameController.useAbility(cardWithAbility);
			}
		}
		if ( cardWithAbility instanceof Tech){
			if(((Tech)cardWithAbility).getAbility().hasTarget()){
				startAbilityThreadListener(cardWithAbility);
			}else{
				gameController.useAbility(cardWithAbility);
				removeCardFromHand(cardWithAbility);
			}
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

	private class AbilityThreadListener extends Thread{
		
		private Card cardWithAbility;
		
		public AbilityThreadListener(Card cardWithAbility){
			setAbilityTargetSelected(false);
			this.cardWithAbility = cardWithAbility;
			InfoPanelGUI.append("Ability target thread started...waiting for input.");
		}
		
		public void run(){
			while(getAbilityTargetSelected()==false){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			InfoPanelGUI.append("Ability target thread stopped.");
			abilitySelectThread = null;
		}

		public void setAbilityTarget(int targetId, Lanes targetLane) {
			Ability ability = null;
			
			if(cardWithAbility instanceof Tech){
				((Tech)cardWithAbility).getAbility().setTarget(targetId, targetLane);
				ability = ((Tech)cardWithAbility).getAbility();
				gameController.useAbility(cardWithAbility);
				removeCardFromHand(cardWithAbility);
			}
			if(cardWithAbility instanceof HeroicSupport){
				((HeroicSupport)cardWithAbility).getAbility().setTarget(targetId, targetLane);
				ability = ((HeroicSupport)cardWithAbility).getAbility();
				tapCard(((HeroicSupport)cardWithAbility).getId(), ((HeroicSupport)cardWithAbility).getLaneEnum());
				gameController.useAbility(cardWithAbility);
			}
			
			InfoPanelGUI.append(cardWithAbility.toString()+"\n uses ability"+ability.toString());
			setAbilityTargetSelected(true);
		}

		
	}
	/**
	 * The thread waits for input from a player to get a target to attack
	 * 
	 * @author patriklarsson
	 *
	 */
	private class AttackThreadListener extends Thread {
		private Unit attacker;
		private Object defender;

		public AttackThreadListener() {
			InfoPanelGUI.append("Attack Thread Started");
			targetSelected = false;
		}

		public void setAttacker(Card card) {
			this.attacker = (Unit) card;
		}

		public void setDefender(Object defender) {
			this.defender = defender;
			setTargetSelected(true);
			InfoPanelGUI.append(attacker.toString() + " attacks " + defender.toString());
			InfoPanelGUI.append("Commit move or choose more units to attack with.");
			tapCard(attacker.getId(), attacker.getLaneEnum());
		}

		@Override
		public void run() {
			while (!isTargetSelected() || Thread.interrupted()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// TODO Add the attacker and defender to the attack object
			Attack attack = gameController.getAttack();
			if (defender instanceof Unit || defender instanceof HeroicSupport) {
				attack.setOpponents(attacker.getId(), ((Card) defender).getId());
			} else {
				attack.setOpponents(attacker.getId(), (int) defender);
			}

			InfoPanelGUI.append("Target Selected. Attack Thread stopped");
			attackSelectThread = null;
		}

	}

	private class DefendThreadListener extends Thread {
		private Unit defender;
		private Unit attacker;

		public DefendThreadListener(Unit defender) {
			this.defender = defender;
			InfoPanelGUI.append("Defend thread started... waiting for target.");
		}

		public void changeAttackersTarget(Unit attacker) {
			this.attacker = attacker;

			int i = gameController.getAttack().getAttacersIndex(attacker.getId());
			if (i == -1) {
				InfoPanelGUI.append("Invalid target, this unit is not attacking.");
			} else {
				gameController.getAttack().setDefender(defender.getId(), i);
				InfoPanelGUI.append(defender.toString() + " will block " + attacker.toString());
				InfoPanelGUI.append("Target changed, commit defense or choose more defenders.");
				tapCard(defender.getId(), defender.getLaneEnum());

			}
			setDefendingTargetSelected(true);
		}

		public void run() {
			while (!getDefendingTargetSelected() || Thread.interrupted()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			defendSelectThread = null;
		}

	}
	
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
			// TODO Ska den ligga här!?

		}
	}

}
