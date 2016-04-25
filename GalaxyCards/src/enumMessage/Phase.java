package enumMessage;

import java.io.Serializable;

/**
 * Conatins the diffrent phases the players in the game can be.
 * @author patriklarsson
 *
 */
public enum Phase implements Serializable {
	IDLE,
	ATTACKING,
	DEFENDING;
}
