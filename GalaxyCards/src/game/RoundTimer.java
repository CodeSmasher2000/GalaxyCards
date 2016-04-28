package game;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import guiPacket.InfoPanelGUI;

public class RoundTimer {
	Toolkit toolkit;
	Timer timer;
	
	public RoundTimer() {
		toolkit = Toolkit.getDefaultToolkit();
		timer = new Timer();
		long delay = 70000;
		long second = 1000;
		timer.schedule(new RemindPlayerTask(), delay ,1* second);
	}
	
	class RemindPlayerTask extends TimerTask	{
		int roundCountdown = 20;
		public void run() {
			if(roundCountdown > 0) {
				toolkit.beep();
				InfoPanelGUI.append(roundCountdown + "seconds remaing to make a move", "RED");
				//System.out.println(roundCountdown + "seconds remaing to make a move");
				roundCountdown--;
			}
			else {
				InfoPanelGUI.append("Round ended", "RED");
				//TODO end round
				timer.cancel();
			}
		}
	}
//	public static void main(String[] args) {
//		System.out.println("Timer startad");
//		new RoundTimer();
//	}
}
