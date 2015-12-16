package br.edu.ifpb.sockchat.connectionBehavior;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import br.edu.ifpb.sockchat.exception.InvalidCommandException;
import br.edu.ifpb.sockchat.tasks.RenameTask;
import br.edu.ifpb.sockchat.tasks.SendAllTask;
import br.edu.ifpb.sockchat.tasks.SendUserTask;
import br.edu.ifpb.sockchat.tasks.Task;
import br.edu.ifpb.sockchat.tasks.TaskWorker;

public class ClientHandler implements Runnable{
	ClientConn client;
	private DataInputStream input;
	private Random randomName;
	
	public ClientHandler(Socket sock){
		this.randomName = new Random(100000);
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
							new Thread(new TaskWorker(new RenameTask(this.client.getName(),command[1]))).start();
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
		String name = "GUEST-"+this.randomName.nextInt();
		return (ConnectionMap.getInstance().getClient(name) != null) ? this.generateGuestName(): name;
	}
	
	public String[] getCommandWords(String command){
		return command.split(" ");
	}
	
	//- Callers
	public Task callSpecificSendWorker(String[] command) throws Exception{
		Task task;
		String msg = "";
		
		switch(command[1]){
			case "-all":
				for(int i = 2; i <= command.length-1;i++) msg += " "+command[i];
				task = new SendAllTask(this.client.getName(),msg);
				break;
			case "-user":
				for(int i = 3; i <= command.length-1;i++) msg = " "+command[i];
				task = new SendUserTask(this.client.getName(),command[2],msg);
				break;
			default:
				throw new InvalidCommandException();
		}
		
		return task;
	}
	
	

}
