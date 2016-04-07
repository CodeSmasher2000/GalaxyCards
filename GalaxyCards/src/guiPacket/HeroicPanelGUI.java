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

import EnumMessage.Persons;
import cards.HeroicSupport;
import exceptionsPacket.GuiContainerException;

/**
 * GUI class that represents the field on which HeroicSupport objects are
 * placed. This field can hold maximum number of two objects. Each object is
 * placed in a layeredPane on the bottommost layer. When feedback is sent it
 * will be drawn over the object that is the target of the feedback.
 * 
 * @author 13120dde
 *
 */

// TODO for feedback, add a object to the layeredPanes second layer when
// feedback is done, remove the layer.
// TODO draw a cool background image for this pane maybe?
public class HeroicPanelGUI extends JPanel {

	// LayeredPanes hold the objects b/c feedback will be drawn over them
	private JLayeredPane heroicPane1, heroicPane2;
	private HeroicSupport[] heroicUnits = new HeroicSupport[2];
	private HeroicMouseListener listener = new HeroicMouseListener();
	private BoardGuiController boardController;
	private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Persons ENUM;

	public HeroicPanelGUI(BoardGuiController boardController, Persons ENUM) {

		this.boardController = boardController;
		boardController.addHeroicPanelListener(this, ENUM);

		initiateLayeredPanes();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createHorizontalStrut(5));
		this.add(Box.createHorizontalGlue());
		this.add(heroicPane1);
		this.add(Box.createHorizontalStrut(5));
		this.add(Box.createHorizontalGlue());
		this.add(heroicPane2);
		this.add(Box.createHorizontalGlue());
		this.add(Box.createHorizontalStrut(5));
		this.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	private void initiateLayeredPanes() {
		heroicPane1 = new JLayeredPane();
		heroicPane1.setLayout(null);
		heroicPane1.setBorder(border);
		heroicPane1.setPreferredSize(new Dimension(156, 226));

		heroicPane2 = new JLayeredPane();
		heroicPane2.setLayout(null);
		heroicPane2.setBorder(border);
		heroicPane2.setPreferredSize(new Dimension(156, 226));
	}

	/**
	 * Attempts to place the object passed as argument in the container on
	 * board. If there is no more room in container exception will be thrown
	 * that is catched in the handGui class to prevent loosing the card from
	 * hand if it cant be placed. The boolean value returned by this method
	 * should be used to refund the player the resource cost of attempting to
	 * play this card. placed.
	 * 
	 * @param heroicSupport
	 *            : HeroicSupport
	 * @return boolean
	 * @throws GuiContainerException
	 */
	public boolean addHeroicSupport(HeroicSupport heroicSupport) throws GuiContainerException {

		boolean okToPlace = false;

		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] == null) {
				heroicUnits[i] = heroicSupport;
				heroicUnits[i].setBounds(0, 10, heroicUnits[i].getPreferredSize().width,
						heroicUnits[i].getPreferredSize().height);
				heroicUnits[i].addMouseListener(listener);
				okToPlace = true;
				if (i == 0) {
					heroicPane1.add(heroicUnits[i], new Integer(0));
					heroicPane1.setBorder(null);
					heroicPane1.repaint();

				} else {
					heroicPane2.add(heroicUnits[i], new Integer(0));
					heroicPane2.setBorder(null);
					heroicPane2.repaint();
				}

				break;
			}
		}

		if (!okToPlace) {
			throw new GuiContainerException("You can only have 2 Heroic Support cards in play");
		}
		return okToPlace;
	}

	/**
	 * If the object passed in as argument is displayed in this panel then the
	 * object will be removed from the panel and returned to the method caller.
	 * 
	 * @param target
	 *            : HeroicSupport
	 * @return target : HeroicSupport
	 */
	public HeroicSupport removeHeroicSupport(HeroicSupport target) {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] == target) {
				if (i == 0) {
					heroicPane1.remove(heroicUnits[i]);
					heroicPane1.setBorder(border);
					heroicPane1.repaint();
				} else {
					heroicPane2.remove(heroicUnits[i]);
					heroicPane2.setBorder(border);
					repaint();
				}
				heroicUnits[i] = null;
			}
		}

		return target;
	}

	// DEBUGG. Listeners will be in seperate classes and the objects will be
	// passed in to the constructor.
	private class HeroicMouseListener implements MouseListener {

		private HeroicSupport temp;
		private Border defaultBorder;
		private Border highlightB = BorderFactory.createLineBorder(CustomGui.borderMarked, 3, true);

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent event) {
			temp = (HeroicSupport) event.getSource();
			defaultBorder = temp.getBorder();
			temp.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			temp.setBorder(defaultBorder);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			temp = (HeroicSupport) event.getSource();

			// Debugging. should send the object to controller or w/e to
			// calculate the damages and remove if the objects defensive value
			// is 0
			removeHeroicSupport(temp);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
