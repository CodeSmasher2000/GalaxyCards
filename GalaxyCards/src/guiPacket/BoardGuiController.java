package guiPacket;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import EnumMessage.Lanes;
import EnumMessage.Persons;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import exceptionsPacket.GuiContainerException;
import exceptionsPacket.NoLaneSelectedException;

/**
 * This class is responsible for message-passing between the gui elements and
 * the Controller object.
 * 
 * @author 13120dde
 *
 */
public class BoardGuiController {

	private HandGUI handGuiPlayer;
	private HeroicPanelGUI heroicGui, enemyHeroicGui;
	private HeroGUI heroGui;
	private OpponentHandGUI handGuiOpponent;
	private ArrayLayeredPane playerDefLane;
	private ArrayLayeredPane playerOffLane;
	private ArrayLayeredPane enemyDefLane;
	private ArrayLayeredPane enemyOffLane;
	
	private ArrayLayeredPane tempLane;
	private LaneSelectListener selectLane;
	private boolean laneSelected = false;
	private LaneSelectThread laneselector;
	
	private Unit unitToPlay; 
	

	// skapa association med controller

	/**
	 * Add a reference to the handGui so that the communication between
	 * boardController and handGUI goes both ways.
	 * 
	 * @param hand
	 *            : HandGUI
	 */
	public void addHandPanelListener(HandGUI hand) {
		handGuiPlayer = hand;
	}

	public void addOpponentHandListener(OpponentHandGUI hand) {
		handGuiOpponent = hand;
	}

	/**
	 * Add a reference to the heroicPanel so that he communication bewtween
	 * boardController and heroicPanel goes both ways.
	 * 
	 * @param heroicPanelGUI
	 */
	public void addHeroicPanelListener(HeroicPanelGUI heroicPanelGUI, Persons ENUM) {
		if(ENUM==Persons.PLAYER){
			heroicGui = heroicPanelGUI;
		}
		if(ENUM==Persons.OPPONENT){
			enemyHeroicGui = heroicPanelGUI;
		}
	}

	public void addHeroListener(HeroGUI heroGui) {
		this.heroGui = heroGui;
	}

	public void addLaneListener(ArrayLayeredPane arrayLayeredPane, Lanes ENUM) {
		if (ENUM == Lanes.PLAYER_DEFENSIVE) {
			playerDefLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.PLAYER_OFFENSIVE) {
			playerOffLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.ENEMY_DEFENSIVE) {
			enemyDefLane = arrayLayeredPane;
		}
		if (ENUM == Lanes.ENEMY_OFFENSIVE) {
			enemyOffLane = arrayLayeredPane;
		}

	}

	// ***MESSAGES SENT TO GUI ELEMENTS************************************
	// *** Methods in this section are called upon from the system to ***
	// *** update various gui elements. ***
	// ********************************************************************

	/**
	 * Attempts to place the Card object passed in as argument to the handGui
	 * container. Throws exception if there is no more space for cards. Maximum
	 * amount of cards on hand = 8.
	 * 
	 * @param card
	 * @throws GuiContainerException
	 */
	public void drawCard(Card card) throws GuiContainerException {
		handGuiPlayer.addCard(card);
	}

	/**
	 * Attempts to play the Heroic Support object passed in as argument and
	 * place it on the corresponding Gui container. Throws exception if there is
	 * no more space. Maximum amount of Heroic Support objects on board = 2.
	 * 
	 * @param cardToPlay
	 * @throws GuiContainerException
	 */
	private void playHeroicSupport(HeroicSupport cardToPlay) throws GuiContainerException {

		// For some reason the same instance of a Card cant be placed in
		// different containers, the object will not be drawn. Need to clone.
		HeroicSupport clonedCard = new HeroicSupport(cardToPlay.getName(), cardToPlay.getRarity(),
				cardToPlay.getImage(), cardToPlay.hasAbility(), cardToPlay.getPrice(), cardToPlay.getDefense());
		cardToPlay = null;
		heroicGui.addHeroicSupport(clonedCard);
	}

	private void playUnitCard(Unit cardToPlay) throws GuiContainerException {
		Unit clonedCard = new Unit(cardToPlay.getName(), cardToPlay.getRarity(), cardToPlay.getImage(),
				cardToPlay.hasAbility(), cardToPlay.getAttack(), cardToPlay.getDefense(), cardToPlay.getPrice());
		cardToPlay = null;

		clonedCard.shrink();
		if(tempLane.getLaneType()==Lanes.PLAYER_DEFENSIVE){
			playerDefLane.addUnit(clonedCard);
		}
		if(tempLane.getLaneType()==Lanes.PLAYER_OFFENSIVE){
			playerOffLane.addUnit(clonedCard);
			System.out.println(playerOffLane.length());
		}

	}

	public void opponentDrawsCard() throws GuiContainerException {
		handGuiOpponent.drawCard();
	}

	public void opponentPlaysUnit(Unit unit, Lanes ENUM) throws GuiContainerException {
		unit.shrink();
		if(ENUM==Lanes.PLAYER_DEFENSIVE){
			enemyDefLane.addUnit(unit);
			handGuiOpponent.playCard();
		}
		if(ENUM==Lanes.PLAYER_OFFENSIVE){
			enemyOffLane.addUnit(unit);
			handGuiOpponent.playCard();
		}
	}
	
	
	public void opponentPlaysHeroicSupport(HeroicSupport hs) throws GuiContainerException {
		enemyHeroicGui.addHeroicSupport(hs);
		handGuiOpponent.playCard();
	}

	// ***MESSAGES SENT TO THE SYSTEM**************************************
	// *** Methods in this section are called upon from the GUI elements***
	// *** to update various gui elements. ***
	// ********************************************************************

	public void addCardToHand(Card card) {
		// controller.addCardToHand(card);
	}

	public void clearHand() {
		// controller.clearHand();
	}

	public void getCardType(Card playCard) {
		// TODO Auto-generated method stub

	}

	public String getHeroName() {
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
	 */

	public void selectLane() throws  NoLaneSelectedException{
		// TODO Auto-generated method stub
		if(laneselector==null){
			laneselector = new LaneSelectThread();
			laneselector.start();
		}
		
		if(!laneSelected){
			throw new NoLaneSelectedException("Thread for selecting lane started...waiting for input");
		}
		
	}
	public void playCard(Card card) throws GuiContainerException, NoLaneSelectedException {
		if (card instanceof ResourceCard) {

		}
		if (card instanceof Unit) {
			unitToPlay = (Unit) card;
			selectLane();
		}
		if (card instanceof HeroicSupport) {
			HeroicSupport cardToPlay = (HeroicSupport) card;

			// Debugging. the flow of events need to send the cardObject to
			// class responsible for determining if player has enough of
			// resources to play this card. Or Rules.getInstance() ?
			playHeroicSupport(cardToPlay);
		}
		if (card instanceof Tech) {

		}

	}
	
	public void setSelectedLane() throws GuiContainerException {
		// TODO Auto-generated method stub
		/*laneSelected = true
		 * stoppa tråden
		 * 
		 * 
		 */
		playUnitCard(unitToPlay);
		handGuiPlayer.playCard(unitToPlay);
		laneSelected = true;
	}
	
	private class LaneSelectThread extends Thread{
		
		
		public LaneSelectThread(){
			selectLane = new LaneSelectListener();
			playerOffLane.addMouseListener(selectLane);
			playerDefLane.addMouseListener(selectLane);
			
			playerOffLane.setBorder(BorderFactory.createTitledBorder("OFFENSIVE LANE"));
			playerDefLane.setBorder(BorderFactory.createTitledBorder("DEFENSIVE LANE"));
			
			playerOffLane.setOpaque(true);
			playerOffLane.setBackground(new Color(0,169,255,75));
			
			playerDefLane.setOpaque(true);
			playerDefLane.setBackground(new Color(0,169,255,75));
		}
		
		public void run(){
			while(!laneSelected){
				System.err.println("Waiting for selection of lane");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			playerOffLane.removeMouseListener(selectLane);
			playerDefLane.removeMouseListener(selectLane);
			
			playerOffLane.setBorder(null);
			playerDefLane.setBorder(null);
			
			playerOffLane.setOpaque(false);
			playerDefLane.setOpaque(false);
			
			selectLane = null;
			laneSelected=false;
			laneselector=null;
			System.err.println("Lane select thread stopped");
		}
	}
	
	private class LaneSelectListener implements MouseListener {

		
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent event) {
			if(event.getSource()==playerDefLane){
				playerDefLane.setBorder(BorderFactory.createLineBorder(CustomColors.borderMarked, 5, true));
			}
			if(event.getSource()==playerOffLane){
				playerOffLane.setBorder(BorderFactory.createLineBorder(CustomColors.borderMarked, 5, true));
			}
			
		}

		@Override
		public void mouseExited(MouseEvent event) {
			if(event.getSource()==playerDefLane){
				playerDefLane.setBorder(BorderFactory.createTitledBorder("DEFENSIVE LANE"));
			}
			if(event.getSource()==playerOffLane){
				playerOffLane.setBorder(BorderFactory.createTitledBorder("OFFENSIVE LANE"));
			}
		}

		@Override
		public void mousePressed(MouseEvent event) {
			tempLane = (ArrayLayeredPane) event.getSource();
			try {
				setSelectedLane();
			} catch (GuiContainerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}


}
