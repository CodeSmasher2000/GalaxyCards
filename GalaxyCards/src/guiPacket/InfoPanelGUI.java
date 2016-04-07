package guiPacket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import cards.Unit;

/**
 * GUI class that contains events for when a card is hovered over. The last card
 * hovered over will be shown in a panel.
 * 
 * @author emilsundberg, 13120dde
 *
 */
public class InfoPanelGUI extends JPanel {
	private BoardGuiController boardController;
	
	private JPanel cardContainer = new JPanel();
	private JPanel cardPanel1 = new JPanel();
	private JPanel cardPanel2 = new JPanel();
	private JPanel midEmpty = new JPanel();
	
	private JPanel twContainer = new JPanel();
	private JPanel twPanel1= new JPanel();
	private JPanel twPanel2= new JPanel();
	
	
	private Border b1 = BorderFactory.createEmptyBorder(0, 10, 0, 10);
	
	private static JTextArea textArea = new JTextArea();
	private static JScrollPane sp = new JScrollPane( textArea );


	public InfoPanelGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		boardController.addHoveredCardlListener(this);
		
		customizeCardPanel();
		customizeTWPanel();
		customizeTextArea();
		
		//FOR DEBUGGING
//		showPanelBorders();
		
		this.setLayout(new GridLayout(3,1));
		this.setOpaque(true);
		this.add(cardPanel2);
		this.add(midEmpty);
		this.add(twPanel2);

	}

	private void customizeTextArea() {
		// TODO Auto-generated method stub
		textArea.setOpaque(true);
		textArea.setLineWrap( true );
		textArea.setWrapStyleWord( true );
		textArea.setEditable(false);
		
		sp.setOpaque(false);
		sp.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	private void showPanelBorders() {
		this.setBorder(BorderFactory.createTitledBorder("MAIN PANEL"));
		
		cardPanel1.setBorder(BorderFactory.createTitledBorder("CARD PANEL 1"));
		cardPanel2.setBorder(BorderFactory.createTitledBorder("CARD PANEL 2"));
		cardContainer.setBorder(BorderFactory.createTitledBorder("CARD CONTAINER"));
		
		twPanel1.setBorder(BorderFactory.createTitledBorder("TW PANEL 1"));
		twContainer.setBorder(BorderFactory.createTitledBorder("TW Container"));
		midEmpty.setBorder(BorderFactory.createTitledBorder("MIDEMPTY"));
		sp.setBorder(BorderFactory.createTitledBorder("SP"));
	}

	private void customizeTWPanel() {
		twContainer.setLayout(new BorderLayout());
		twContainer.add(sp, BorderLayout.CENTER);
		twContainer.setOpaque(true);
		

		twPanel1.setLayout(new BoxLayout(twPanel1, BoxLayout.X_AXIS));
		twPanel1.add(Box.createHorizontalStrut(10));
		twPanel1.add(twContainer);
		twPanel1.add(Box.createHorizontalStrut(10));
		
		twPanel2.setLayout(new BoxLayout(twPanel2, BoxLayout.Y_AXIS));
		twPanel2.add(twPanel1);
		twPanel2.add(Box.createVerticalStrut(10));
		
	}

	private void customizeCardPanel() {
		
		cardContainer.setOpaque(false);
		cardContainer.setBorder(b1);
		
		cardPanel2.setLayout(new BoxLayout(cardPanel2, BoxLayout.Y_AXIS));
		cardPanel2.add(Box.createVerticalStrut(50));
		cardPanel2.add(cardPanel1);

		cardPanel1.setLayout(new BoxLayout(cardPanel1, BoxLayout.X_AXIS));
		cardPanel1.add(Box.createHorizontalGlue());
		cardPanel1.add(Box.createHorizontalStrut(100));
		cardPanel1.add(cardContainer);
		cardPanel1.add(Box.createHorizontalStrut(100));
		cardPanel1.add(Box.createHorizontalGlue());
		
	}

	/**
	 * recieves a Card object which is then displayed in a panel
	 * 
	 * @param cardContainer
	 *            - the card to show
	 */
	public void showCard(Unit cardToShow) {
		cardContainer.removeAll();
		cardToShow.setBorder(CustomGui.highlightBorder);
		cardContainer.setPreferredSize(new Dimension(cardToShow.getPreferredSize().width, cardToShow.getPreferredSize().height));
		cardContainer.add(cardToShow);

	}
	
	public static void setText( final String txt )  {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.setText( txt );
				JScrollBar bar = sp.getVerticalScrollBar();
				bar.setValue( bar.getMaximum()-bar.getVisibleAmount() );
			}
		});
	}

	public static void setText( Object obj )  {
		setText( obj.toString() );
	}
	
	public static void append( final String txt ) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textArea.append( txt +"\n");
			}
		});
	}
	
	public static void append( Object obj ) {
		append( obj.toString() );
	}
	
	public static void println() {
		append( "\n" );
	}
	
	public static void println( String txt ) {
		append( txt + "\n" );
	}
	
	public static void println( Object obj ) {
		println( obj.toString() );
	}
}

