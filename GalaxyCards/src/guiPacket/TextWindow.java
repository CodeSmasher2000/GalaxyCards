package guiPacket;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TextWindow {
	private static JPanel frame = new JPanel();
	private static JTextArea ta = new JTextArea();
	private static JScrollPane sp = new JScrollPane( ta );
	
	public static void setText( final String txt )  {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ta.setText( txt );
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
				ta.append( txt );
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
