package cardCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import cards.CardTestClass;

public class CreateGui extends JPanel {
	
	private JTabbedPane tabs = new JTabbedPane();
	private JPanel mainPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel previewPanel = new JPanel();

	private CreateUnit createUnit = new CreateUnit();
	private CreateResource createResource = new CreateResource();
	private CreateTech createTech = new CreateTech();
	private CreateHeroic createHeroic = new CreateHeroic();
	
	
	
	
	public CreateGui(){
		JFrame frame1 = new JFrame("Card Creator");
		frame1.setLayout(new GridLayout(1,3));
//		setPreferredSize(new Dimension(1240,860));
//		setMinimumSize(new Dimension(800,600));
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		tabs.addTab("Unit", createUnit);
		tabs.addTab("Resource", createResource);
		tabs.addTab("Tech", createTech);
		tabs.addTab("Heroic", createHeroic);
		frame1.add(deckPanel);
		frame1.add(tabs);
		frame1.add(previewPanel);
		frame1.pack();
		
	}
	

	
	public static void main(String[] args) {
	new CreateGui();
	}

}
