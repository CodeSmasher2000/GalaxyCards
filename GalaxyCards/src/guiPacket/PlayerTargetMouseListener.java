package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;
import enumMessage.Lanes;
import enumMessage.Phase;

public class PlayerTargetMouseListener implements MouseListener {
	private Card card;
	private HeroGUI heroGui;
	private Border cardDefaultBorder, heroDefaultBorder;
	private Border highlightB = BorderFactory.createLineBorder(CustomGui.playerColor, 3, true);
	private BoardGuiController boardController;

	public PlayerTargetMouseListener(BoardGuiController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mouseClicked(MouseEvent event) {

		// card = (Card) event.getSource();
		// if(card instanceof Unit) {
		// boardController.startAttackThreadListner();
		// boardController.setAttacker(card);
		// }
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (event.getSource() instanceof Card) {

			card = (Card) event.getSource();
			cardDefaultBorder = card.getBorder();
			card.setBorder(BorderFactory.createCompoundBorder(highlightB, cardDefaultBorder));
			if (card instanceof Unit) {
				boardController.updateInfoPanelCard((Unit) card);
			}
		}
		if (event.getSource() instanceof HeroGUI) {
			heroGui = (HeroGUI) event.getSource();
			// heroDefaultBorder=heroGui.getBorder();
			// heroGui.setBorder(highlightB);
		}

	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (event.getSource() instanceof Card) {
			card.setBorder(cardDefaultBorder);
		}
		if (event.getSource() instanceof HeroGUI) {
			// heroGui.setBorder(heroDefaultBorder);
		}

	}

	@Override
	public void mousePressed(MouseEvent event) {

		if (boardController.getPhase() == Phase.DEFENDING) {
			if (event.getSource() instanceof Unit) {
				card = (Unit) event.getSource();

				if (((Unit) card).getLaneEnum() == Lanes.PLAYER_DEFENSIVE) {
					if (((Unit) card).getTap() == false) {
						boardController.startDefendThreadListener(card);
					} else {
						InfoPanelGUI.append("This unit is tapped, can't defend.");
					}

				} else {
					InfoPanelGUI.append("This unit is not in defensive lane, can't defend");
				}
			}
		}

		if (boardController.getPhase() == Phase.ATTACKING) {

			if (event.getSource() instanceof Card) {
				card = (Card) event.getSource();
				if (card instanceof Unit) {
					if (((Unit) card).getTap()) {
						InfoPanelGUI.append("The card is tapped, can't attack.");
					} else {
						boardController.startAttackThreadListner();
						boardController.setAttacker(card);
					}
				}
				if (card instanceof HeroicSupport) {
					if (((HeroicSupport) card).getTap()) {
						InfoPanelGUI.append("The card is tapped, can't use ability.");
					} else {
						InfoPanelGUI.append("TODO: use ability: Area of effect / target: world  ");
					}
				}
			}
			if (event.getSource() instanceof HeroGUI) {
				InfoPanelGUI.append(heroGui.toString(), null);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
