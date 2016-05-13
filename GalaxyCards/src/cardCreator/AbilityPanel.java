package cardCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abilities.Ability;
import abilities.DrawCardAbility;
import abilities.SingleTargetAbility;
import abilities.TapTargetAbility;
import abilities.UntapTargetAbility;

public class AbilityPanel extends JPanel{
	private JComboBox<String> cbAbility = new JComboBox<String>();
	private JLabel lblTf = new JLabel("Value");
	private JLabel lblAbility = new JLabel("Choose ability: ");
	private JTextField tfValue = new JTextField();
	private JTextArea taDescription = new JTextArea();
	private JLabel lblDescription = new JLabel("Description");
	
	public AbilityPanel(){
		setPreferredSize(new Dimension(350,300));
		setLayout(new FlowLayout());
		initElems();
		ButtonListener btnListener = new ButtonListener();
		cbAbility.addItemListener(btnListener);
		
	}
	
	public void initElems(){
		lblTf.setPreferredSize(new Dimension(320,10));
		lblDescription.setPreferredSize(new Dimension(320,10));
		lblAbility.setPreferredSize(new Dimension(320,10));
		tfValue.setPreferredSize(new Dimension(320,20));
		taDescription.setPreferredSize(new Dimension(320,120));
		add(lblAbility);
		add(cbAbility);
		add(lblTf);
		add(tfValue);
		add(lblDescription);
		add(taDescription);
	}
	
	public void initComboBox(){
		String healdmg = "Heal/dmg";
		String drawCard = "Draw Card";
		String tapTarget = "Tap Target";
		String untapTarget = "Untap Target";
		String buff = "Buff";
		cbAbility.setPreferredSize(new Dimension(320,20));
		cbAbility.addItem(healdmg);
		cbAbility.addItem(drawCard);
		cbAbility.addItem(tapTarget);
		cbAbility.addItem(untapTarget);
		cbAbility.addItem(buff);
	}
	
	public Ability createAbility(){
		if(cbAbility.getSelectedItem().equals("Heal/dmg")){
			return createHealdmg();
		}else if(cbAbility.getSelectedItem().equals("Draw Card")){
			return createDrawCard();
		}else if(cbAbility.getSelectedItem().equals("Tap Target")){
			return createTapTarget();
		}else if (cbAbility.getSelectedItem().equals("Untap Target")){
			return createUntapTarget();
		}
		return null;
	}
	
	private Ability createUntapTarget() {
		UntapTargetAbility untap = new UntapTargetAbility(getDescription());
		return untap;
	}

	private Ability createTapTarget() {
		TapTargetAbility tap = new TapTargetAbility(getDescription());
		return tap;
	}

	private DrawCardAbility createDrawCard() {
		DrawCardAbility drawCard = new DrawCardAbility(getDescription(),getValue());
		return drawCard;
	}

	private SingleTargetAbility createHealdmg() {
		SingleTargetAbility healdmg = new SingleTargetAbility(getValue(), getDescription());
		 return healdmg;
		
	}

	public String getDescription(){
		String description = taDescription.getText();
		return description;
	}
	
	public int getValue(){
		int value = Integer.parseInt(tfValue.getText());
		return value;
	}

	public void setDescription(String description) {
		taDescription.setText(description);
		
	}

	public void setValue(int value) {
		tfValue.setText(Integer.toString(value));
		
	}
	
	private class ButtonListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			
		}

		
		
	}

}
