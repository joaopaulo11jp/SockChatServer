package br.edu.ifpb.sockchat.connectionBehavior;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import br.edu.ifpb.sockchat.exception.InvalidCommandException;
import br.edu.ifpb.sockchat.tasks.RenameTask;
import br.edu.ifpb.sockchat.tasks.SendAllTask;
import br.edu.ifpb.sockchat.tasks.SendUserTask;
import br.edu.ifpb.sockchat.tasks.Task;
import br.edu.ifpb.sockchat.tasks.TaskWorker;

public class ClientHandler implements Runnable{
	ClientConn client;
	private DataInputStream input;
	
	public ClientHandler(Socket sock){
		this.client = new ClientConn(this.generateGuestName(),sock);
		
		ConnectionMap.getInstance().addClient(this.client);
		try{
			this.input = new DataInputStream(this.client.getSock().getInputStream());
		}catch(IOException e){
			System.out.println("Erro:"+e.getMessage());
		}
	}
	
	@Override
	public void run() {
		String[] command;
		
		while(true){			
			try {
				command = getCommandWords(waitReq());
				
				switch(command[0]){				
					case "send":
						try{
						new Thread(new TaskWorker(this.callSpecificSendWorker(command))).start();
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
						break;
					case "list":
						break;
					case "rename":
						if(command.length == 2)
							new Thread(new TaskWorker(new RenameTask(this.client.getName(),command[1])));
						else throw new InvalidCommandException();
						break;
					case "bye":
						break;
					default:
						throw new InvalidCommandException();				
				}		
				
			} catch (InvalidCommandException e){
				//TODO os erros devem ser enviados ao cliente
				System.out.println(e.getMessage());
			} catch (IOException e) {
				if(e.getMessage() == null){
					System.out.println(client.getName()+" disconnected");
					try {
						client.getSock().close();
						Thread.currentThread().stop();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	public String waitReq() throws IOException{
		return this.input.readUTF();
		
	}
	
	public String generateGuestName(){
		//TODO implementar de verdade!
		return "guest-123";
	}
	
	public String[] getCommandWords(String command){
		return command.split(" ");
	}
	
	//- Callers
	public Task callSpecificSendWorker(String[] command) throws Exception{
		Task task;
		
		switch(command[1]){
			case "-all":
				task = new SendAllTask(this.client.getName(),command[2]);
				break;
			case "-user":
				task = new SendUserTask(this.client.getName(),command[2],command[3]);
				break;
			default:
				throw new InvalidCommandException();
		}
		
		return task;
	}
	
	

}
