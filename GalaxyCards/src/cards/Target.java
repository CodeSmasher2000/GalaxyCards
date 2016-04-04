package cards;

/**
 * The interface should be implemented by Cards that should be able to be marked
 * on the board
 * @author patriklarsson
 *
 */
public interface Target {
	public void damage(int amt);
	public void heal(int amt);
}
