
package enumMessage;
import java.io.Serializable;





	/**
	 * Klass som innehåller enums för olika commands.
	 * @author Jonte
	 *
	 */
public enum Commands  implements Serializable {
	LOGIN(0),
	OK (1),
	NOTOK (2),
	DISCONNECT(3),
	GETHERO(4),
	MATCHMAKING_MATCH_FOUND(5),
	MATCHMAKING_STOP(6),
	MATCHMAKING_START(7),
	MATCH_MOVE(8);
	
	private int value;
	private static final long serialVersionUID = 42L;

	 Commands(int value){
		this.value = value;
	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
