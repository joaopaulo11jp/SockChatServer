package br.edu.ifpb.sockchat.tasks;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;
import br.edu.ifpb.sockchat.exception.UserIsNotConnectedException;
import br.edu.ifpb.sockchat.exception.UserSelfMessageException;

public class SendUserTask implements Task{
	private String fromName;
	private String toName;
	private String msg;
	
		public SendUserTask(String fromName, String toName, String msg){
			this.fromName = fromName;
			this.toName = toName;
			this.msg = msg;
		}
	
	@Override
	public void doTask() {
		ClientConn toClient = ConnectionMap.getInstance().getClient(toName);
		ClientConn fromClient = ConnectionMap.getInstance().getClient(fromName);
		SimpleDateFormat sdfTime; 
		SimpleDateFormat sdfDate;
		Date date;
		
		try{
			if(toClient == null) throw new UserIsNotConnectedException(toName);
			else if(toClient == fromClient) throw new UserSelfMessageException();
			
			sdfTime = new SimpleDateFormat("HH:mm");
			sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			date = Calendar.getInstance().getTime(); 
			
			String msg = toClient.getSock().getInetAddress().toString()+
			             ":"+toClient.getSock().getPort()+
			             "/~"+this.fromName+":"+
			             this.msg+" "+sdfTime.format(date)+" "+
			             sdfDate.format(date)+" (PVT";
			
			toClient.getOut().writeUTF(msg+" to you)");
			
			fromClient.getOut().writeUTF(msg+" to "+toClient.getName()+")");
		}catch (IOException e){
			System.out.println("Internal Error: "+e.getMessage());
		}catch(Exception e){
			try {
				fromClient.getOut().writeUTF("Error: "+e.getMessage());
			} catch (IOException e1) {
				System.out.println("Internal Error: "+e1.getMessage());
			}
		}
		
	}

}
