package cardCreator;

/**
 * The Class is used to start the card creator
 * @author patriklarsson
 *
 */
public class Start {
	public static void main(String[] args) {
		CreateController controller = new CreateController();
		controller.setGui(new CreateGui(controller));
	}
}
