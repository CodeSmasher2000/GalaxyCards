package game;

import Client.ClientController;
import cards.Unit;
import move.Attack;

public class DefendingPhase {
	private GameController gc;
	private ClientController cc;
	private Attack attack;
	
	public DefendingPhase(GameController gc, ClientController cc, Attack attack) {
		this.gc = gc;
		this.cc = cc;
		this.attack = attack;
	}
	
	public void addMove(Unit defender, Unit attacker) {
		int index = attack.getIndex(attacker);
		attack.setDefender(defender, index);
	}
	
	public void commitMove() {
		
	}
}
