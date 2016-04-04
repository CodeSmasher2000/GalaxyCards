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

import cards.Unit;
import exceptionsPacket.NoPlaceOnBoardException;

public class ArrayLayeredPane extends JPanel {

	private int nbrOfElements;
	private JLayeredPane[] layerArray;
	private Unit[] units;
	private MouseListener listener = null;

	/**
	 * Instantiate this object with a int passed in as argument which tells how
	 * many Card objects this container will have room for.
	 * 
	 * @param nbrOfElements
	 */
	public ArrayLayeredPane(int nbrOfElements) {

		this.nbrOfElements = nbrOfElements;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
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
	public ArrayLayeredPane(int nbrOfElements, MouseListener listener) {
		this(nbrOfElements);
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
	
	public void addUnit(Unit unit) throws NoPlaceOnBoardException {

		boolean okToPlace = false;

		for (int i = 0; i < units.length; i++) {
			if (units[i] == null) {
				units[i] = unit;
				units[i].setBounds(0, 10, units[i].getPreferredSize().width,
						units[i].getPreferredSize().height);
				if(listener!=null){
					units[i].addMouseListener(listener);
				}
				okToPlace = true;
				// 0 1 2 3 4 5 
				int index = 3;
				int x = 1;
				while(index>=0 && index<=5){
					if(layerArray[index].getComponent(0)==null){
						layerArray[index].add(units[i], new Integer(0));
						index=10;
					}
					else{
						index-=x;
						x=-x;
						x++;
					}
				}
				break;
			}
		}
		
		if(!okToPlace){
			throw new NoPlaceOnBoardException("You can only have 2 Heroic Support cards in play");
		}
	}

}
