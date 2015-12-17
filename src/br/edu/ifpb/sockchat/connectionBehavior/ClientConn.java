package br.edu.ifpb.sockchat.connectionBehavior;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConn {
	private String name;
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	
	    public ClientConn(String name, Socket sock) throws IOException{
	    	this.name = name;
	    	this.sock = sock;
	    	this.in = new DataInputStream(sock.getInputStream());
	    	this.out = new DataOutputStream(sock.getOutputStream());
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
	
	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	
	public synchronized void sendOut(String txt) throws IOException{
		this.out.writeUTF(txt);
	}
	
}
