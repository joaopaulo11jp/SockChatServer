package br.edu.ifpb.sockchat.tasks;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;

public class ListTask implements Task{
	private String fromName;
	
		public ListTask(String fromName){
			this.fromName = fromName;
		}
	
	@Override
	public void doTask() {
		ClientConn client = ConnectionMap.getInstance().getClient(fromName);
		Set<String> clientNames= ConnectionMap.getInstance().getAllClientsNames();
		
		try{
			
			String msg = "---------------\n";
			msg += "Users List\n";
			msg += "---------------\n";
			Iterator<String> it = clientNames.iterator();
			while(it.hasNext()){
				msg += it.next()+"\n";
			}
			
			msg += "---------------\n";
			client.getOut().writeUTF(msg);
		}catch(IOException e){
			System.out.println("Internal Error: "+e.getMessage());
		}
	}

}
