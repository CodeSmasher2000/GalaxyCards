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
import javax.swing.SwingUtilities;

import abilities.Ability;
import abilities.DrawCardAbility;
import abilities.SingleTargetAbility;
import abilities.TapTargetAbility;
import abilities.UntapTargetAbility;

public class AbilityPanel extends JPanel {
	private JComboBox<String> cbAbility = new JComboBox<String>();
	private JLabel lblTf = new JLabel("Value");
	private JLabel lblAbility = new JLabel("Choose ability: ");
	private JTextArea tfValue = new JTextArea();
	private JTextArea taDescription = new JTextArea();
	private JLabel lblDescription = new JLabel("Description");

	public AbilityPanel() {
		setPreferredSize(new Dimension(350, 300));
		setLayout(new FlowLayout());
		initElems();
		ButtonListener btnListener = new ButtonListener();
		cbAbility.addItemListener(btnListener);

	}
	
	/**
	 * Initializes the Swing componenents and adds them to the main panel. 
	 */
	public void initElems() {
		initComboBox();
		lblTf.setPreferredSize(new Dimension(320, 10));
		lblDescription.setPreferredSize(new Dimension(320, 10));
		lblAbility.setPreferredSize(new Dimension(320, 10));
		tfValue.setPreferredSize(new Dimension(320, 20));
		taDescription.setPreferredSize(new Dimension(320, 120));
		add(lblAbility);
		add(cbAbility);
		add(lblTf);
		add(tfValue);
		add(lblDescription);
		add(taDescription);
	}
	
	
	/**
	 * Initializes the JComboBoxes and gives them a String.
	 */
	public void initComboBox() {
		String healdmg = "Heal/dmg";
		String drawCard = "Draw Card";
		String tapTarget = "Tap Target";
		String untapTarget = "Untap Target";
//		String buff = "Buff";
		cbAbility.setPreferredSize(new Dimension(320, 20));
		cbAbility.addItem(healdmg);
		cbAbility.addItem(drawCard);
		cbAbility.addItem(tapTarget);
		cbAbility.addItem(untapTarget);
//		cbAbility.addItem(buff);
	}
	/**
	 * Creates an ability depending on the chosen String in the JComboBox.
	 * @return
	 * 		The ability to create.
	 */
	public Ability createAbility() {
		if (cbAbility.getSelectedItem().equals("Heal/dmg")) {
			return createHealdmg();
		} else if (cbAbility.getSelectedItem().equals("Draw Card")) {
			return createDrawCard();
		} else if (cbAbility.getSelectedItem().equals("Tap Target")) {
			return createTapTarget();
		} else if (cbAbility.getSelectedItem().equals("Untap Target")) {
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
		DrawCardAbility drawCard = new DrawCardAbility(getDescription(), getValue());
		return drawCard;
	}

	private SingleTargetAbility createHealdmg() {
		SingleTargetAbility healdmg = new SingleTargetAbility(getValue(), getDescription());
		return healdmg;

	}
	
	
	public String getDescription() {
		String description = taDescription.getText();
		return description;
	}
	
	/** 
	 * Converts the String value to an int.
	 * @return
	 * 		The value of an ability.
	 */
	public int getValue() {
		String text = tfValue.getText();
		System.out.println(text);
		int value = Integer.parseInt(text);
		return value;
	}

	public void setDescription(String description) {
		taDescription.setText(description);

	}

	/**
	 * Inner class that contains the logic when an itemlistener is called.
	 * @author Jonte
	 *
	 */
	private class ButtonListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (((String) e.getItem()).equalsIgnoreCase("Heal/dmg")) {
				tfValue.setText("Heal = -value, Dmg = +value");
				tfValue.setEnabled(true);
			} else if (((String) e.getItem()).equalsIgnoreCase("Draw Card")) {
				tfValue.setText("Value = how many cards to draw.");
				tfValue.setEnabled(true);
			} else if (((String) e.getItem()).equalsIgnoreCase("Tap Target")) {
				tfValue.setText("no value");
				tfValue.setEnabled(false);
			} else if (((String) e.getItem()).equalsIgnoreCase("Untap Target")) {
				tfValue.setText("no value");
				tfValue.setEnabled(false);
			}

		}
	}
}
