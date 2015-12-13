package br.edu.ifpb.sockchat.connectionBehavior;

import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.edu.ifpb.sockchat.exception.NameAlreadyExistsException;

public class ConnectionMap {
	private Map<String,ClientConn> clients;
	private static ConnectionMap instance;
	
		private ConnectionMap() {
			this.clients = new HashMap<String,ClientConn>();
		}
	
	public static ConnectionMap getInstance(){
        if(instance == null) instance = new ConnectionMap();
		return instance;
	}
	
	public synchronized void addClient(ClientConn client){
		this.clients.put(client.getName(),client);
	}
	
	public synchronized void removeClient(String name){
		this.clients.remove(name);
	}
	
	public synchronized ClientConn changeClientName(String oldName, String newName) throws NameAlreadyExistsException{		
		if(this.clients.get("oldname") != null) throw new NameAlreadyExistsException();
		
		this.clients.put(newName, this.clients.remove(oldName));
		
		return this.clients.get(newName);
	}
	
	public Socket getClientSocket(String name){
		return this.clients.get(name).getSock();
	}
	
	public ClientConn getClient(String name){
		return this.clients.get(name);
	}
	
	public Set<String> getAllClientsNames(){
		return this.clients.keySet();
	}
	
	public Collection<ClientConn> getAllClient(){
		return this.clients.values();
	}
	
}
