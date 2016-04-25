package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;

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
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
			heroDefaultBorder=heroGui.getBorder();
			heroGui.setBorder(highlightB);
		}

	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (event.getSource() instanceof Card) {
			card.setBorder(cardDefaultBorder);
		}
		if (event.getSource() instanceof HeroGUI) {
			heroGui.setBorder(heroDefaultBorder);
		}

	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getSource() instanceof Card) {

			card = (Card) event.getSource();
			if (card instanceof Unit) {
				// Initiate Attack
				InfoPanelGUI.append("//TODO: attack target: opponent Hero/HeroicSupport cards", null);

			}
			if (card instanceof HeroicSupport) {
				InfoPanelGUI.append("TODO: use ability: Area of effect / target: world  ", null);
			}
		}
		if(event.getSource() instanceof HeroGUI){
			InfoPanelGUI.append(heroGui.toString(), null);
		
		}
		if(card instanceof HeroicSupport){
			InfoPanelGUI.append("TODO: use ability: Area of effect / target: world  ","YELLOW");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
