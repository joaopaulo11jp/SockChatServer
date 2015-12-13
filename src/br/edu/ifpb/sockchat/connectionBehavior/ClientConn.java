package br.edu.ifpb.sockchat.connectionBehavior;

import java.net.Socket;

public class ClientConn {
	private String name;
	private Socket sock;
	
	    public ClientConn(String name, Socket sock){
	    	this.name = name;
	    	this.sock = sock;
	    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSock() {
		return sock;
	}
	
}
