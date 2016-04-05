package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import EnumMessage.Lanes;
import cards.Unit;
import exceptionsPacket.NoEmptySpaceInContainer;

public class ArrayLayeredPane extends JPanel {

	private int nbrOfElements;
	private JLayeredPane[] layerArray;
	private Unit[] units;
	private MouseListener listener = null;
	private BoardGuiController boardController;
	private Lanes LANE; 

	/**
	 * Instantiate this object with a int passed in as argument which tells how
	 * many Card objects this container will have room for.
	 * 
	 * @param nbrOfElements
	 */
	public ArrayLayeredPane(BoardGuiController boardController, Lanes ENUM, int nbrOfElements) {

		this.boardController=boardController;
		
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

		setElementBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

	}

	/**
	 * Overloaded constructor that takes a MouseListener object as argument. the
	 * mouselistener obj will be added to every Card object placed in this
	 * container.
	 * 
	 * @param nbrOfElements
	 * @param listener
	 */
	public ArrayLayeredPane(BoardGuiController boardController, Lanes ENUM, int nbrOfElements, MouseListener listener) {
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
	
	public boolean addUnit(Unit unit) throws NoEmptySpaceInContainer {

		boolean okToPlace = false;
		
		int endIndex = units.length-1;
		int startIndex = 0;
		
		int steps = (endIndex+1-startIndex);
		int index = (startIndex+endIndex)>>1;
		int stepdir = 1;
		for(int q=0; q<steps; q++, index+=stepdir*q, stepdir=-stepdir)
		{
			if(units[index]==null){
				units[index]=unit;
				units[index].setBounds(0, 0, units[index].getPreferredSize().width,
						units[index].getPreferredSize().height);
				if(listener!=null){
					units[index].addMouseListener(listener);
				}
				okToPlace = true;
				layerArray[index].add(units[index], new Integer(0));
				break;
			}
		}
		if(!okToPlace){
			throw new NoEmptySpaceInContainer("You can only have 6 units per lane");
		}
		return okToPlace;
	}

}
