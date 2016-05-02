package move;

import java.io.Serializable;

import cards.HeroicSupport;
import cards.Unit;
import guiPacket.HeroGUI;

/**
 * 
 * @author emilsundberg, jonte
 *
 */

public class Attack implements Serializable {

	private int[] offensive = new int[6];
	private int[] defensive = new int[6];
	private int index = 0;

	public void setOpponents(int attacker, int defender) {
		offensive[index] = attacker;
		defensive[index] = defender;
		index++;
	}
	
	/**
	 * Returns a unit in a index in the offensive array
	 * @param index
	 * 		The index to get a target from
	 * @return
	 * 		Returns a unit object
	 */
	public int getAttacker(int index) {
		return offensive[index];
	}
	
	/**
	 * Returns a Object of a defender in the defender array
	 * @param index
	 * 		The index to get the defender from
	 * @return
	 * 		A Object rerpesenting a defender. Will be a instance of either
	 * 		HeroicSupport or Hero.
	 */
	public int getDefender(int index) {
		return offensive[index];
	}

//	public void fight() {
//		for (int index = this.index; index >= 0; index--) {
//			Unit attacker = (Unit) offensive[index];
//			int attackerAtt = attacker.getAttack();
//			int attackerDef = attacker.getDefense();
//			if (defensive[index] instanceof Unit) {
//				Unit blocker = (Unit) defensive[index];
//				int blockerAtt = blocker.getAttack();
//				int blockerDef = blocker.getDefense();
//				if ((blockerDef - attackerAtt) <= 0) {
//
//				} else {
//					blocker.setDefense(blockerDef - attackerAtt);
//				}
//				if ((attackerDef - blockerAtt) <= 0) {
//					// TODO skicka till scrapyard
//				} else {
//					attacker.setDefense(attackerDef - blockerAtt);
//				}
//			} else if (defensive[index] instanceof HeroicSupport) {
//				HeroicSupport hsBlocker = (HeroicSupport) defensive[index];
//				int hsDef = hsBlocker.getDefense();
//				if ((hsDef - attackerAtt <= 0)) {
//					// TODO skicka till scrapyard
//				}
//			} else {
//				// TODO Hero.dealDamage
//
//			}
//		}
//	}
	
//	public int getIndex(Unit unit) {
//		for (int i = 0; i < index; i++) {
//			if (unit.compareTo(offensive[i])== 0) {
//				return i;
//			}
//		}
//		return-1;
//	}
	
	public int getLength() {
		return this.index;
	}

	public void setDefender(int defender, int index) {
		defensive[index] = defender;
	}

	public void Defend(int defender, int target) {
		for (int i = 0; i < offensive.length; i++) {
			if (offensive[i] == target) {
				defensive[i] = defender;
			}
		}

	}

}
