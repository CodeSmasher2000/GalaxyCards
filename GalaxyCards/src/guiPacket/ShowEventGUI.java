package guiPacket;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ShowEventGUI extends JPanel{
	private JTextArea eventField = new JTextArea();
	
	public ShowEventGUI(){
		eventField.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(eventField);
		add(scrollPane);
	}
	
	private void addEvent(String event) {
		
	}

}
