package guiPacket;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

/**
 * This class har static custom elements used by the system's gui. The elements
 * are such as custom colors, borders and fonts.
 * 
 * @author 13120dde
 *
 */
public class CustomGui {

	// Colors
	public static Color playerColor = new Color(0, 190, 255);
	public static Color opponentColor = new Color(247, 61, 66);
	public static Color guiTransparentColor = new Color(255, 255, 255, 55);
	public static Color blueHighlight = new Color(0, 169, 255);
	
	// Fonts
	public static Font fontBig = new FontUIResource("BankGothic Md BT", Font.ITALIC, 22);
	public static Font fontSmall = new FontUIResource("BankGothic Md BT", Font.ITALIC, 16);

	// Borders
	public static Border highlightBorder = BorderFactory.createLineBorder(CustomGui.playerColor, 3, true);

	public static Border defLaneMarkedBorder = BorderFactory.createTitledBorder(CustomGui.highlightBorder,
			"DEFENSIVE LANE", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, CustomGui.fontBig,
			CustomGui.blueHighlight);
	public static Border offLaneMarkedBorder = BorderFactory.createTitledBorder(CustomGui.highlightBorder,
			"OFFENSIVE LANE", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, CustomGui.fontBig,
			CustomGui.blueHighlight);

	public static Border offLaneBorder = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.WHITE, 1, true), "OFFENSIVE LANE", TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, CustomGui.fontSmall, Color.WHITE);
	public static Border deffLaneBorder = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.WHITE, 1, true), "DEFENSIVE LANE", TitledBorder.LEFT,
			TitledBorder.DEFAULT_POSITION, CustomGui.fontSmall, Color.WHITE);

}
