package br.edu.ifpb.sockchat.tasks;

import java.io.IOException;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;

public class ByeTask implements Task{
	private String fromName;
	
		public ByeTask(String fromName){
			this.fromName = fromName;
		}
	@Override
	public void doTask() {
		ClientConn client = ConnectionMap.getInstance().getClient(fromName);
		ConnectionMap.getInstance().removeClient(fromName);
		
		try {
			client.getOut().writeUTF("Server: You're disconnected!");
			client.getSock().close();
		} catch (IOException e) {
			System.out.println("Internal Error: "+e.getMessage());
		}
	}

}
