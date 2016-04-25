package move;

import cards.HeroicSupport;
import cards.Unit;
import guiPacket.HeroGUI;

/**
 * 
 * @author emilsundberg, jonte
 *
 */

public class Attack {
	
	private Unit[] offensive = new Unit[6];
	private Object[] defensive = new Object[6];
	private int index = 0;
	
	private void setOpponents(Unit attacker,Object defender) {
			offensive[index] = attacker;
			defensive[index] = defender;
			index++;
			
			
	}
	
	private void fight(){
		for(int index = this.index; index>0;index--) {
			Unit attacker = (Unit) offensive[index];
			int attackerAtt = attacker.getAttack();
			int attackerDef = attacker.getDefense();
			if(defensive[index] instanceof Unit) {
				Unit blocker =(Unit) defensive[index];
				int blockerAtt = blocker.getAttack();
				int blockerDef = blocker.getDefense();
				if((blockerDef-attackerAtt)<=0) {
					
				}
				else {
					blocker.setDefense(blockerDef-attackerAtt);
				}
				if((attackerDef-blockerAtt)<=0) {
					//TODO skicka till scrapyard
				}
				else {
					attacker.setDefense(attackerDef-blockerAtt);
				}
			}
			else if(defensive[index] instanceof HeroicSupport) {
				HeroicSupport hsBlocker = (HeroicSupport) defensive[index];
				int hsDef = hsBlocker.getDefense();
				if((hsDef-attackerAtt<=0)){
					//TODO skicka till scrapyard
				}
			}
			else {
				//TODO Hero.dealDamage
				
				
			}
		}
	}
	private void setDefender(Unit defender, int index) {
		defensive[index] = defender;
	}
	
	

}
