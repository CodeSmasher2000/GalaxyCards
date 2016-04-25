package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;

public class PlayerTargetMouseListener implements MouseListener {
	private Card card;
	private Border defaultBorder;
	private Border highlightB = BorderFactory.createLineBorder(CustomGui.playerColor, 3, true);
	private BoardGuiController boardController;
	
	public PlayerTargetMouseListener(BoardGuiController boardController){
		this.boardController=boardController;
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		card = (Card) event.getSource();
		if(card instanceof Unit) {
			boardController.startAttackThreadListner();
			boardController.setAttacker(card);
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		card = (Card) event.getSource();
		defaultBorder = card.getBorder();
		card.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));
		if (card instanceof Unit){
			boardController.updateInfoPanelCard((Unit) card);
		}
		

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		card.setBorder(defaultBorder);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		card = (Card) event.getSource();
		if(card instanceof Unit){
			//Initiate Attack
			InfoPanelGUI.append("//TODO: attack target: opponent Hero/HeroicSupport cards");
			
		}
		if(card instanceof HeroicSupport){
			InfoPanelGUI.append("TODO: use ability: Area of effect / target: world  ");
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
