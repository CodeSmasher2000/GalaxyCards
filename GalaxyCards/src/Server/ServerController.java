package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import cards.Deck;
import cards.ResourceCard;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import game.Hero;

/**
 * Klass som tar hand om nya anslutningar och lagrar dem i en TreeMap.
 * @author Jonte
 *
 */

public class ServerController {
	
	private  SortedMap<String, ClientHandler> userMap =
			Collections.synchronizedSortedMap(new TreeMap<String, ClientHandler>());
	private List<String> usersLookingForGame = Collections.synchronizedList(
			new ArrayList<String>());
	
	public ServerController() {
		new LookingForGameThread().start();
	}
	
	
	/**
	 * Metod som kontrollerar om klientens användarnamn finns. Om det finns så skickas
	 * godkännande till klienten och användarnamnet läggs det till i en TreeMap.
	 *  Om användarnamnet redan finns får klienten mata in nytt.
	 * @param userName
	 * 			Klientens inmatade användarnamn
	 * @param clientHandler
	 * 				klientens ClientHandler-objekt.
	 */
	public void login(String userName, ClientHandler clientHandler){
		if(userMap.containsKey(userName)){
			CommandMessage loginFalse = new CommandMessage(Commands.NOTOK,"Server");
			clientHandler.writeMessage(loginFalse);
			System.out.println("Login fail, enter new username");
		}else{
			CommandMessage loginOK = new CommandMessage(Commands.OK,"Server");
			clientHandler.writeMessage(loginOK);
			clientHandler.setActiveUser(userName);
			userMap.put(userName, clientHandler);
			System.out.println("Login OK");
		}

	}
	
	/**
	 * Tar bort en ansluten klient från listan
	 * @param clientHandler
	 */
	public void disconnect(ClientHandler clientHandler){
		synchronized(userMap){
			userMap.remove(clientHandler.getActiveUser());
			printUsers();
		}
	}
	
	/**
	 * Skriver ut alla användare i TreeMappen.
	 * @return
	 * 		Returnerar en sträng som innehåller alla användare.
	 */
	public String printUsers(){
		String userList = null;
		StringBuffer sb = new StringBuffer("Users Online: ");
		Iterator iterator =	userMap.keySet().iterator();
		while(iterator.hasNext()){
			 sb.append(iterator.next() + "\n");
			 userList = sb.toString();
			 System.out.println(userList);
		}
		return userList;
	}
	
	/**
	 * Adds a user to the looking for gamelist
	 */
	public void addUserToMatchMaking(String userToAdd) {
		usersLookingForGame.add(userToAdd);
	}
	
	public void sendHero(ClientHandler clientHandler){
		System.out.println("sendHero är kallad");
		//The following code is only for test purpose
		Deck deck = new Deck(); 
		for (int i = 0; i<5;i++){
			deck.addCard(new ResourceCard());
		}
		Hero hero = new Hero("Testa Patteson");
		hero.setDeck(deck);
		CommandMessage commandMessage = new CommandMessage(Commands.GETHERO,"Server",hero);
		clientHandler.writeMessage(commandMessage);
	}
	
	private class LookingForGameThread extends Thread {
		
		public void LookForGame() {
			while(true) {
				// If there are more than two users a match can be created
				if (usersLookingForGame.size() >= 2) {
					matchFound();
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void matchFound() {
			// LOG MESSAGE FOR DEBUG PURPOSE
			System.out.println("Server: MatchFound()");
			
			// Using the remove method frpm userLookingForGame becuase if a match
			// is found they are not longer looking for a match.
			ClientHandler ch1 = userMap.get(usersLookingForGame.remove(0));
			ClientHandler ch2 = userMap.get(usersLookingForGame.remove(0));
			Match match = new Match(ch1, ch2);
			// TODO Kanske ska skicka med match objectet
			ch1.writeMessage(new CommandMessage(Commands.MATCHMAKING_MATCH_FOUND,
					"Server"));
			ch2.writeMessage(new CommandMessage(Commands.MATCHMAKING_MATCH_FOUND,
					"Server"));
		}
		
		
		@Override
		public void run() {
			LookForGame();
		}
	}
}
