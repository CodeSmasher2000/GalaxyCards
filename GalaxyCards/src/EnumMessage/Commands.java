package EnumMessage;

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
	DISCONNECT(3);
	
	private int value;
	private static final long serialVersionUID = 42L;

	 Commands(int value){
		this.value = value;
	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
