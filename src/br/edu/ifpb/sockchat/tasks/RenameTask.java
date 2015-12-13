package br.edu.ifpb.sockchat.tasks;

import java.io.DataOutputStream;
import java.io.IOException;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;
import br.edu.ifpb.sockchat.exception.NameAlreadyExistsException;

public class RenameTask implements Task{
	private String oldName;
	private String newName;
	
		public RenameTask(String oldName,String newName) {
			this.oldName = oldName;
			this.newName = newName;
		}
	@Override
	public void doTask() {
		ClientConn client =  ConnectionMap.getInstance().getClient(oldName);
		DataOutputStream output = null; 	
		
		try{
			output = new DataOutputStream(client.getSock().getOutputStream());
			ConnectionMap.getInstance().changeClientName(oldName, newName);	
			output.writeUTF("Nome alterado de :"+oldName+" para: "+newName);
		}catch(NameAlreadyExistsException e){
			try {
				output.writeUTF("Erro: "+e.getMessage());
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
		}catch(Exception e){
			
		}
		
	}

}
