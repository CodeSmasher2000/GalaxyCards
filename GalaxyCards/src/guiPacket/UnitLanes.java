package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import EnumMessage.Lanes;
import cards.Unit;
import exceptionsPacket.GuiContainerException;

public class UnitLanes extends JPanel {

	private JLayeredPane[] layerArray;
	private Unit[] units;
	private BoardGuiController boardController;
	private Lanes ENUM;

	private int nbrOfElements;
	private int index =-1;
	
	private OpponentTargetMouseListener opponentListener;
	private PlayerTargetMouseListener playerListener;

	/**
	 * Instantiate this object with a int passed in as argument which tells how
	 * many Card objects this container will have room for.
	 * 
	 * @param nbrOfElements
	 */
	public UnitLanes(BoardGuiController boardController, Lanes ENUM, int nbrOfElements) {

		this.boardController = boardController;
		playerListener = new PlayerTargetMouseListener(boardController);
		opponentListener = new OpponentTargetMouseListener(boardController);
		this.ENUM = ENUM;
		this.nbrOfElements = nbrOfElements;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		boardController.addLaneListener(this, ENUM);
		units = new Unit[nbrOfElements];
		layerArray = new JLayeredPane[nbrOfElements];

		this.add(Box.createHorizontalStrut(10));

		for (int i = 0; i < nbrOfElements; i++) {
			layerArray[i] = new JLayeredPane();
			layerArray[i].setLayout(null);
			layerArray[i].setPreferredSize(new Dimension(156, 150));

			this.add(Box.createHorizontalGlue());
			this.add(layerArray[i]);
			this.add(Box.createHorizontalGlue());
			this.add(Box.createHorizontalStrut(10));
		}
	}

	/**
	 * Returns the number of elements that this container can hold. Notice that
	 * it does not specify if there are any elements in this container or not.
	 * 
	 * @return
	 */
	public int length() {
		return nbrOfElements+1;
	}
	
	public Unit[] getUnits(){
		return units;
	}

	/**
	 * Return a enum value for this specific object. Lanes should have different
	 * mouse interaction depending if they belong to the player or to the
	 * opponent.
	 * 
	 * @return Lanes : PLAYER_OFFENSIVE, PLAYER_DEFENSIVE, ENEMY_OFFENSIVE,
	 *         ENEMY_DEFENSIVE
	 */
	public Lanes getLaneType() {
		return ENUM;
	}

	/**
	 * Set border for every layeredPane in this container.
	 * 
	 * @param border
	 *            : Border
	 */
	public void setElementBorder(Border border) {
		for (int i = 0; i < nbrOfElements; i++) {
			layerArray[i].setBorder(border);
		}
	}

	public boolean addUnit(Unit unit) throws GuiContainerException {

		boolean okToPlace = false;

		int endIndex = units.length - 1;
		int startIndex = 0;

		int steps = (endIndex + 1 - startIndex);
		int index = (startIndex + endIndex) >> 1;
		int stepdir = 1;
		for (int q = 0; q < steps; q++, index += stepdir * q, stepdir = -stepdir) {
			if (units[index] == null) {
				units[index] = unit;
				units[index].setBounds(0, 0, units[index].getPreferredSize().width,
						units[index].getPreferredSize().height);
				if (ENUM==Lanes.ENEMY_DEFENSIVE || ENUM==Lanes.ENEMY_OFFENSIVE) {
					units[index].addMouseListener(opponentListener);
				}else{
					units[index].addMouseListener(playerListener);
				}
				okToPlace = true;
				layerArray[index].add(units[index], new Integer(0));
				layerArray[index].setBorder(null);
				break;
			}
		}
		if (!okToPlace) {
			throw new GuiContainerException("You can only have 6 units per lane");
		}
		return okToPlace;
	}

	public Unit removeUnit(Unit target) {
		for (int i = 0; i < units.length; i++) {
			if (units[i] == target) {
				layerArray[i].remove(target);
//				units[i].removeMouseListener(listener);
				units[i].setBorder(null);
				units[i].enlarge();
				if(ENUM==Lanes.PLAYER_DEFENSIVE || ENUM==Lanes.PLAYER_OFFENSIVE){
					boardController.addToPlayerScrapyard(boardController.cloneCard(units[i]));
				}else{
					boardController.addToOpponentScrapyard(units[i]);
				}
				units[i] = null;
				repaint();
			}
		}

		return target;
	}
}
