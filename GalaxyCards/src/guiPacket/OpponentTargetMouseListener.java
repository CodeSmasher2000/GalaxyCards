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
	private HeroGUI heroGui;
	private Border heroDefaultBorder, cardDefaultBorder;
	private Border highlightB = BorderFactory.createLineBorder(CustomGui.opponentColor, 3, true);

	private BoardGuiController boardController;

	public OpponentTargetMouseListener(BoardGuiController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
//		if (event.getSource() instanceof HeroicSupport) {
//			boardController.setDefender(card);
//		} else if(event.getSource() instanceof HeroGUI) {
//			boardController.setDefender(heroGui);
//		}
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
			heroGui = (HeroGUI)event.getSource();
//			heroDefaultBorder = heroGui.getBorder();
//			heroGui.setBorder(highlightB);
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if(event.getSource() instanceof Card){
			card.setBorder(cardDefaultBorder);
		}
		if(event.getSource() instanceof HeroGUI){
//			heroGui.setBorder(heroDefaultBorder);
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getSource() instanceof HeroGUI) {
			 InfoPanelGUI.append(heroGui.toString());
			 boardController.setDefender(heroGui); // TODO: This needs to be changed to the hero gui.
		}
		if (event.getSource() instanceof Card) {
			InfoPanelGUI.append("Target: " + card.toString(),"RED");
			InfoPanelGUI.append(Integer.toString(System.identityHashCode(card)),"RED");
		}
		
		if (event.getSource() instanceof HeroicSupport) {
//			boardController.setDefender(card);
			boardController.setDefender((HeroicSupport)event.getSource());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
