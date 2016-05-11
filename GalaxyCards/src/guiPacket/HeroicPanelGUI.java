package guiPacket;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;
import enumMessage.Persons;
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
public class HeroicPanelGUI extends JPanel {

	private JLayeredPane heroicPane1, heroicPane2;
	private HeroicSupport[] heroicUnits = new HeroicSupport[2];
	private BoardGuiController boardController;
	private Persons ENUM;

	private OpponentTargetMouseListener opponentListener;
	private PlayerTargetMouseListener playerListener;

	private ImageIcon background;

	public HeroicPanelGUI(BoardGuiController boardController, Persons ENUM) {
		this.ENUM = ENUM;
		if (ENUM == Persons.PLAYER) {
			background = new ImageIcon("files/pictures/heroicPanelTexture2.jpg");
		} else {
			background = new ImageIcon("files/pictures/heroicPanelTexture.jpg");
		}

		this.boardController = boardController;
		playerListener = new PlayerTargetMouseListener(boardController);
		opponentListener = new OpponentTargetMouseListener(boardController);
		boardController.addHeroicPanelListener(this, ENUM);

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				initiateLayeredPanes();
				setLayout(new BoxLayout(HeroicPanelGUI.this, BoxLayout.X_AXIS));
				add(Box.createHorizontalStrut(5));
				add(Box.createHorizontalGlue());
				add(heroicPane1);
				add(Box.createHorizontalStrut(5));
				add(Box.createHorizontalGlue());
				add(heroicPane2);
				add(Box.createHorizontalGlue());
				add(Box.createHorizontalStrut(5));
				
			}
		});
	}

	private void initiateLayeredPanes() {
		heroicPane1 = new JLayeredPane();
		heroicPane1.setLayout(null);
		heroicPane1.setPreferredSize(new Dimension(156, 226));

		heroicPane2 = new JLayeredPane();
		heroicPane2.setLayout(null);
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
	protected void addHeroicSupport(HeroicSupport heroicSupport) throws GuiContainerException {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < heroicUnits.length; i++) {
					if (heroicUnits[i] == null) {
//						heroicSupport.setPersonEnum(ENUM);
						heroicUnits[i] = heroicSupport;
						heroicUnits[i].setBounds(0, 10, heroicUnits[i].getPreferredSize().width,
								heroicUnits[i].getPreferredSize().height);
						// if ENUM is player or opponent add different listener
						if (ENUM == Persons.PLAYER) {
							heroicUnits[i].addMouseListener(playerListener);
						}
						if (ENUM == Persons.OPPONENT) {
							heroicUnits[i].addMouseListener(opponentListener);
						}
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
			}
		});
	}

	/**
	 * If the object passed in as argument is displayed in this panel then the
	 * object will be removed from the panel and returned to the method caller.
	 * 
	 * @param target
	 *            : HeroicSupport
	 * @return target : HeroicSupport
	 */
	protected HeroicSupport removeHeroicSupport(HeroicSupport target) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < heroicUnits.length; i++) {
					if (heroicUnits[i] == target) {
						if (i == 0) {
							heroicPane1.remove(heroicUnits[i]);
							heroicPane1.repaint();
						} else {
							heroicPane2.remove(heroicUnits[i]);
							repaint();
						}
						if (ENUM == Persons.OPPONENT) {
							boardController.addToOpponentScrapyard(heroicUnits[i]);
						} else {
							boardController.addToPlayerScrapyard(heroicUnits[i]);
						}
					}
				}
				
			}
		});

		return target;
	}

	/**
	 * Check the defensive value for each Heroic support in this lane. If the
	 * defensive value is equal to or less than 0 then the unit object will get
	 * removed.
	 */
	public void checkStatus() {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] != null) {
				if (heroicUnits[i].getDefense() <= 0) {
					removeHeroicSupport(heroicUnits[i]);
				}
			}
		}
	}

	/**
	 * Untaps all cards held in this container.
	 * 
	 */
	public void untapAllInLane() {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] != null) {
				heroicUnits[i].untap();
				String[] unitName = heroicUnits[i].toString().split(" ");
				InfoPanelGUI.append(unitName[0] + " is tapped: " + heroicUnits[i].getTap());
			}
		}
	}

	/**
	 * Taps all cards held in this container.
	 */
	public void tapAllInLane() {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] != null) {
				heroicUnits[i].tap();
				String[] unitName = heroicUnits[i].toString().split(" ");
				InfoPanelGUI.append(unitName[0] + " is tapped: " + heroicUnits[i].getTap());
			}
		}

	}

	/**
	 * Taps the card with the same id as argument.
	 * 
	 * @param cardId
	 *            : int
	 */
	public void tapCard(int cardId) {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] != null) {
				if (heroicUnits[i].getId() == cardId) {
					heroicUnits[i].tap();
					String[] unitName = heroicUnits[i].toString().split(" ");
					InfoPanelGUI.append(unitName[0] + " is tapped: " + heroicUnits[i].getTap());
				}
			}
		}
	}

	/**
	 * Untaps the card with the same id as argument.
	 * 
	 * @param cardId
	 *            : int
	 */
	public void untapCard(int cardId) {
		for (int i = 0; i < heroicUnits.length; i++) {
			if (heroicUnits[i] != null) {
				if (heroicUnits[i].getId() == cardId) {
					heroicUnits[i].untap();
					String[] unitName = heroicUnits[i].toString().split(" ");
					InfoPanelGUI.append(unitName[0] + " is tapped: " + heroicUnits[i].getTap());
				}
			}
		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	public boolean updateCard(HeroicSupport cardToUpdate) {
		for (int i = 0; i < heroicUnits.length; i++) {
			// TODO : Change null check?
			if (heroicUnits[i] != null) {
				if (heroicUnits[i].getId() == cardToUpdate.getId()) {
					heroicUnits[i].setDefense(cardToUpdate.getDefense());
					checkStatus();
					return true;
				} 
			}
		}
		return false;
	}
}
