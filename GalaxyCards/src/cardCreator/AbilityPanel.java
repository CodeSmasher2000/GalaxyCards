package cardCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abilities.Ability;
import abilities.DrawCardAbility;
import abilities.SingleTargetAbility;

public class AbilityPanel extends JPanel{
	private JRadioButton rbHealDmg = new JRadioButton("Heal/Damage");
	private JRadioButton rbDrawCard = new JRadioButton("Draw Card");
	private JRadioButton rbTap = new JRadioButton("Tap/untap");
	private Dimension rbSize = new Dimension(100,20);
	private JLabel lblTf = new JLabel("Value");
	private JTextField tfValue = new JTextField();
	private JTextArea taDescription = new JTextArea();
	private JLabel lblDescription = new JLabel("Description");
	
	public AbilityPanel(){
		setPreferredSize(new Dimension(350,300));
		setLayout(new FlowLayout());
		initElems();
		ButtonListener btnListener = new ButtonListener();
		rbDrawCard.addActionListener(btnListener);
		rbHealDmg.addActionListener(btnListener);
		rbTap.addActionListener(btnListener);
		tfValue.addActionListener(btnListener);
		
	}
	
	public void initElems(){
		lblTf.setPreferredSize(new Dimension(320,10));
		lblDescription.setPreferredSize(new Dimension(320,10));
		tfValue.setPreferredSize(new Dimension(320,20));
		taDescription.setPreferredSize(new Dimension(320,120));
		rbDrawCard.setPreferredSize(rbSize);
		rbHealDmg.setPreferredSize(rbSize);
		rbTap.setPreferredSize(rbSize);
		ButtonGroup group = new ButtonGroup();
		group.add(rbHealDmg);
		group.add(rbDrawCard);
		group.add(rbTap);
		add(rbHealDmg);
		add(rbDrawCard);
		add(rbTap);
		add(lblTf);
		add(tfValue);
		add(lblDescription);
		add(taDescription);
	}
	
	public Ability createAbility(){
		if(rbHealDmg.isSelected()){
			return createHealdmg();
		}else if(rbDrawCard.isSelected()){
			return createDrawCard();
		}else if(rbTap.isSelected()){
			return createTap();
		}
	}
	
	private Ability createTap() {
		
		return null;
	}

	private DrawCardAbility createDrawCard() {
		DrawCardAbility drawCard = new DrawCardAbility(getDescription());
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
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==rbHealDmg){
				tfValue.setEnabled(true);
				tfValue.setText("Heal= -value, Damage = +value");
			}else if(e.getSource()==rbDrawCard){
				tfValue.setEnabled(true);
				tfValue.setText("Value = amount of cards");
			}else if(e.getSource()==rbTap){
				tfValue.setEnabled(false);
			}else if(e.getSource() == tfValue){
				tfValue.setText(" ");
			}
			
		}
		
	}

}
