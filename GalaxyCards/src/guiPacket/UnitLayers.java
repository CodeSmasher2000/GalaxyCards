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

public class UnitLayers extends JPanel {

	private int nbrOfElements;
	private JLayeredPane[] layerArray;
	private Unit[] units;
	private MouseListener listener = new UnitMouseListener();
	private BoardGuiController boardController;
	private Lanes ENUM;

	/**
	 * Instantiate this object with a int passed in as argument which tells how
	 * many Card objects this container will have room for.
	 * 
	 * @param nbrOfElements
	 */
	public UnitLayers(BoardGuiController boardController, Lanes ENUM, int nbrOfElements) {

		this.boardController = boardController;
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
	 * Overloaded constructor that takes a MouseListener object as argument. the
	 * mouselistener obj will be added to every Card object placed in this
	 * container.
	 * 
	 * @param nbrOfElements
	 * @param listener
	 */
	public UnitLayers(BoardGuiController boardController, Lanes ENUM, int nbrOfElements, MouseListener listener) {
		this(boardController, ENUM, nbrOfElements);
		this.listener = listener;
	}

	private void addCustomMouseListener(Card card) {
		card.addMouseListener(listener);
	}

	/**
	 * Returns the number of elements that this container can hold. Notice that
	 * it does not specify if there are any elements in this container or not.
	 * 
	 * @return
	 */
	public int length() {
		return nbrOfElements;
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
	 * Adds a titled border to this container with the String passed in as
	 * argument for the border text.
	 * 
	 * @param name
	 *            : String
	 */
	public void setContainerName(String name) {
		this.setBorder(BorderFactory.createTitledBorder(name));
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
				if (listener != null) {
					units[index].addMouseListener(listener);
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
				units[i].removeMouseListener(listener);
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

	// DEBUGG. Listeners will be in seperate classes and the objects will be
	// passed in to the constructor.
	private class UnitMouseListener implements MouseListener {

		private Unit temp;
		private Border defaultBorder;
		private Border highlightB = BorderFactory.createLineBorder(CustomGui.borderMarked, 3, false);

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent event) {
			temp = (Unit) event.getSource();
			defaultBorder = temp.getBorder();
			temp.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));
			boardController.updateHoveredCardGui(temp);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			temp.setBorder(defaultBorder);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			temp = (Unit) event.getSource();

			// Debugging. should send the object to controller or w/e to
			// calculate the damages and remove if the objects defensive value
			// is 0
			removeUnit(temp);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
