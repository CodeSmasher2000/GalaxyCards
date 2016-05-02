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

import cards.Unit;
import enumMessage.Lanes;
import exceptionsPacket.GuiContainerException;

/**
 * This class is responsible for holding unitobjects that are placed on
 * playfield. Each player has access to two lanes, an offensive lane and a
 * defensive lane. Whenever a Unit object is placed in a lane it will get a
 * custom mouselistener depending if the lanes belong to the player or opponent.
 * 
 * @author 13120dde
 *
 */
public class UnitLanes extends JPanel {

	private JLayeredPane[] layerArray;
	private Unit[] units;
	private BoardGuiController boardController;
	private Lanes ENUM;

	private int nbrOfElements;
	private int index = -1;

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
		return nbrOfElements + 1;
	}

	public Unit[] getUnits() {
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

	/**
	 * Attempts to place a Unit object passed in as argument in this container.
	 * If there is no space to place the object a exception is thrown.
	 * 
	 * @param unit
	 *            : Unit
	 * @return : boolean
	 * @throws GuiContainerException
	 */
	public boolean addUnit(Unit unit) throws GuiContainerException {

		boolean okToPlace = false;

		int endIndex = units.length - 1;
		int startIndex = 0;

		int steps = (endIndex + 1 - startIndex);
		int index = (startIndex + endIndex) >> 1;
		int stepdir = 1;
		for (int q = 0; q < steps; q++, index += stepdir * q, stepdir = -stepdir) {
			if (units[index] == null) {
//				unit.setLaneEnum(ENUM);
				units[index] = unit;
				units[index].setBounds(0, 0, units[index].getPreferredSize().width,
						units[index].getPreferredSize().height);
				if (ENUM == Lanes.ENEMY_DEFENSIVE || ENUM == Lanes.ENEMY_OFFENSIVE) {
					units[index].addMouseListener(opponentListener);
				} else {
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

	/**
	 * Removes the target Unit object passed in as argument from this container.
	 * The unit object is then placed in the scrapyard depending if the object
	 * belongs to the player or opponent.
	 * 
	 * @param target
	 *            : Unit
	 * @return target : Unit
	 */
	public Unit removeUnit(Unit target) {
		for (int i = 0; i < units.length; i++) {
			if (units[i] == target) {
				layerArray[i].remove(target);
				// units[i].removeMouseListener(listener);
				units[i].setBorder(null);
				units[i].enlarge();
				if (ENUM == Lanes.PLAYER_DEFENSIVE || ENUM == Lanes.PLAYER_OFFENSIVE) {
					boardController.addToPlayerScrapyard(boardController.cloneCard(units[i]));
				} else {
					boardController.addToOpponentScrapyard(units[i]);
				}
				units[i] = null;
				repaint();
			}
		}

		return target;
	}

	/**
	 * Check the defensive value for each unit in this lane. If the defensive
	 * value is equal to or less than 0 then the unit object will get removed.
	 */
	public void checkStatus() {
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null) {
				if (units[i].getDefense() <= 0) {
					removeUnit(units[i]);
				}
			}
		}
	}

	/**
	 * Untaps all cards held in this container.
	 * 
	 */
	public void untapAllInLane() {
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null) {
				units[i].untap();
				String[] unitName = units[i].toString().split(" ");
				InfoPanelGUI.append(unitName[0] + " is tapped: " + units[i].getTap());
			}
		}
	}

	/**
	 * Taps all cards held in this container.
	 */
	public void tapAllInLane() {
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null) {
				units[i].tap();
				String[] unitName = units[i].toString().split(" ");
				InfoPanelGUI.append(unitName[0] + " is tapped: " + units[i].getTap());
			}
		}

	}

	/**
	 * Taps the card with the same id as argument.
	 * 
	 * @param cardId
	 *            : int
	 */
	public boolean tapCard(int cardId) {
		boolean cardFound = false;
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null) {
				if (units[i].getId() == cardId) {
					units[i].tap();
					cardFound=true;
					String[] unitName = units[i].toString().split(" ");
					InfoPanelGUI.append(unitName[0] + " is tapped: " + units[i].getTap());
				}
			}
		}
		return cardFound;
	}

	/**
	 * Untaps the card with the same id as the argument.
	 * 
	 * @param cardId
	 *            : int
	 */
	public boolean untapCard(int cardId) {
		boolean cardFound = false;
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null) {
				if (units[i].getId() == cardId) {
					units[i].untap();
					cardFound = true;
					String[] unitName = units[i].toString().split(" ");
					InfoPanelGUI.append(unitName[0] + " is tapped: " + units[i].getTap());
				}
			}
		}
		return cardFound;
	}
}
