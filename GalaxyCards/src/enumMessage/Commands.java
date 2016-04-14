
package enumMessage;
import java.io.Serializable;





	/**
	 * Klass som innehåller enums för olika commands.
	 * @author Jonte
	 *
	 */
public enum Commands  implements Serializable {
	LOGIN,
	OK,
	NOTOK,
	DISCONNECT,
	GETHERO,
	MATCHMAKING_MATCH_FOUND,
	MATCHMAKING_STOP,
	MATCHMAKING_START,
	MATCH_PLAYCARD;
	
	private int value;
	private static final long serialVersionUID = 42L;

//	 Commands(int value){
//		this.value = value;
//	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
