package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;

public class OpponentTargetMouseListener implements MouseListener {

	// private HeroGUI hero;
	private Card card;
	private Border defaultBorder;
	private Border highlightB = BorderFactory.createLineBorder(CustomGui.opponentColor, 3, true);

	private BoardGuiController boardController;

	public OpponentTargetMouseListener(BoardGuiController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (event.getSource() instanceof HeroGUI) {
			// hero = (HeroGUI) event.getSource();
		}
		if (event.getSource() instanceof Card) {
			card = (Card) event.getSource();
			defaultBorder = card.getBorder();
			card.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));
		}
		if (card instanceof Unit){
			boardController.updateHoveredCardGui((Unit) card);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		card.setBorder(defaultBorder);
		// hero.setBorder(null);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getSource() instanceof HeroGUI) {
			// InfoPanelGUI.append("Target: " +hero.toString());
		}
		if (event.getSource() instanceof Card) {
			InfoPanelGUI.append("Target: " + card.toString());
			InfoPanelGUI.append(Integer.toString(System.identityHashCode(card)));
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
