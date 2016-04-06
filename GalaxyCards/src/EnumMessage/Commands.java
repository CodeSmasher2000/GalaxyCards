package EnumMessage;
	/**
	 * Klass som innehåller enums för olika commands.
	 * @author Jonte
	 *
	 */
public enum Commands {
	LOGIN(0),
	OK (1),
	NOTOK (2);
	
	private int value;
	
	 Commands(int value){
		this.value = value;
	}
	 
	 public int getValue(){
		 return value;
	 }
	
}
