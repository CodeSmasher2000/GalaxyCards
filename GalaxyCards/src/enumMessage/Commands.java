
package enumMessage;
import java.io.Serializable;





	/**
	 * Klass som innehåller enums för olika commands.
	 * @author Jonte
	 *
	 */
public enum Commands  implements Serializable {

//	LOGIN(0),
//	OK (1),
//	NOTOK (2),
//	DISCONNECT(3),
//	GETHERO(4),
//	MATCHMAKING_MATCH_FOUND(5),
//	MATCHMAKING_STOP(6),
//	MATCHMAKING_START(7),
//	MATCH_PLAYCARD(8);
	LOGIN,
	OK,
	NOTOK,
	DISCONNECT,
	GETHERO,
	MATCHMAKING_MATCH_FOUND,
	MATCHMAKING_STOP,
	MATCHMAKING_START,
	MATCH_UPDATE_HERO,
	MATCH_PLAYCARD, 
	MATCH_INIT_GAME,
	MATCH_OPPONENT_DRAW_CARD,
	MATCH_FRIENDLY_DRAW_CARD,
	MATCH_DRAW_CARD,
	MATCH_ATTACK_MOVE,
	MATCH_DEFEND_MOVE,
	MATCH_SET_PHASE,
	MATCH_NOT_VALID_MOVE,
	MATCH_PLACE_CARD,
	MATCH_NEW_ROUND;
	
	private int value;
	private static final long serialVersionUID = 42L;

//	 Commands(int value){
//		this.value = value;
//	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
