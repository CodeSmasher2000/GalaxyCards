package game;

import Client.ClientController;
import cards.Unit;
import move.Attack;

/**
 * This class contains the methods for doing the correct response when the player
 * is in attacking phase
 * 
 * @author patriklarsson
 *
 */
public class AttackingPhase {
	private GameController gc;
	private ClientController cc;
	private Attack attack;
	
	/**
	 * Creates a AttackingPhase object and gives it a reference to a GameController
	 * and ClientController
	 * @param gc
	 * 		A Reference to the GameController Object
	 * @param cc
	 */
	public AttackingPhase(GameController gc, ClientController cc) {
		this.gc = gc;
		this.cc = cc;
		this.attack = new Attack();
	}
	
	/**
	 * Adds a attacker and defender to the attack move
	 * @param attacker
	 * 		A Unit card witch is a defender
	 * @param defender
	 * 		A Object with the object that should recive the attack
	 */
	public void addMove(Unit attacker, Object defender) {
		attack.setOpponents(attacker, defender);
	}
	
	/**
	 * This is called when a move is commited
	 * @param attack
	 */
	public void commitMoves() {
		gc.commitMove(attack);
	}
}
