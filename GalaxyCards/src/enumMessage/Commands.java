
package enumMessage;
import java.io.Serializable;





	/**
	 * Klass som innehåller enums för olika commands.
	 * @author Jonte
	 *
	 */
public enum Commands  implements Serializable {

	LOGIN,
	LOGIN_OK,
	LOGIN_NOTOK,
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
	MATCH_NEW_ROUND,
	MATCH_REMOVE_CARD,
	MATCH_ADD_TO_OPPONET_SCRAPYARD,
	MATCH_UPDATE_OPPONENT_HERO,
	MATCH_UPDATE_FRIENDLY_HERO,
	MATCH_TAP_CARD,
	MATCH_UNTAP_CARD,
	MATCH_TAP_ALL_IN_LANE,
	MATCH_UNTAP_ALL_IN_LANE,
	MATCH_UPDATECARD, MATCH_ADD_TO_SCRAPYARD;
	
	private int value;
	private static final long serialVersionUID = 42L;

//	 Commands(int value){
//		this.value = value;
//	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
