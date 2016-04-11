package Server;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;


import enumMessage.CommandMessage;
import enumMessage.Commands;

/**
 * Klass som tar hand om nya anslutningar och lagrar dem i en TreeMap.
 * @author Jonte
 *
 */

public class ServerController {
	
	private  SortedMap<String, ClientHandler> userMap =
			Collections.synchronizedSortedMap(new TreeMap<String, ClientHandler >()); 
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
			userMap.remove(clientHandler);
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
}
