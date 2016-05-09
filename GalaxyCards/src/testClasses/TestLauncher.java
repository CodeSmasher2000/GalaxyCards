package testClasses;

import Client.ClientGUI;
import Server.Server;
import Server.ServerController;

public class TestLauncher {
	public static void main(String[] args) {
		new  Server();
		new ClientGUI();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ClientGUI();
	}
}
