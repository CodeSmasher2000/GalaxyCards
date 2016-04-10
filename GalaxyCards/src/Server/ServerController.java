package Server;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import enumMessage.CommandMessage;
import enumMessage.Commands;

public class ServerController {
	
	private SortedMap<String, ClientHandler> userMap =
			Collections.synchronizedSortedMap(new TreeMap<String, ClientHandler >()); 
	
	public void login(String userName, ClientHandler clientHandler){
		if(userMap.containsKey(userName)){
			CommandMessage loginFalse = new CommandMessage(Commands.NOTOK,"Server");
			clientHandler.writeMessage(loginFalse);
		}else{
			CommandMessage loginOK = new CommandMessage(Commands.OK,"Server");
			clientHandler.writeMessage(loginOK);
			userMap.put(userName, clientHandler);
			
		}
	}
}
