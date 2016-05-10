package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;
import enumMessage.Lanes;
import enumMessage.Phase;

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
		if(boardController.getPhase()==Phase.DEFENDING){
			if(boardController.getDefendThreadStarted()){
				if(event.getSource() instanceof Unit && (((Unit)event.getSource()).getLaneEnum()==Lanes.ENEMY_OFFENSIVE)){
					boardController.changeAttackersTarget((Unit)event.getSource());
				}else{
					InfoPanelGUI.append("Invalid target, this unit is not attacking.");
					boardController.setDefendingTargetSelected(true);
				}
			}else{
				InfoPanelGUI.append(event.getSource().toString());
			}
		}
		if(boardController.getPhase()==Phase.ATTACKING){
			if (boardController.getAttackThreadStarted()){
				if (event.getSource() instanceof HeroGUI) {
					HeroGUI hero = (HeroGUI)event.getSource();
					// Kontrollerar om hjälten är firendly
					if (hero.getId() == boardController.getFriendlyHeroId()) {
						InfoPanelGUI.append("Invalid taget, you cannot attack your own hero", "RED");
					} else if(hero.getId() == boardController.getOpponetHeroId()) {
						boardController.setDefender(hero.getId());
					}
					boardController.setDefender(heroGui.getId()); // TODO: This needs to be changed to the hero gui.
				}else if (event.getSource() instanceof HeroicSupport) {
					boardController.setDefender((HeroicSupport)event.getSource());
				}else{
					InfoPanelGUI.append("Invalid target, you can only attack opponent's hero or heroic supports");
					boardController.setTargetSelected(true);
				}
			}else{
				InfoPanelGUI.append(event.getSource().toString());
			}
		}
		if(boardController.getPhase()==Phase.IDLE){
			InfoPanelGUI.append(event.getSource().toString());
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
