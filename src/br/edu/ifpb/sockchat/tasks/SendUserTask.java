package br.edu.ifpb.sockchat.tasks;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;

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
		ClientConn client = ConnectionMap.getInstance().getClient(toName);
		DataOutputStream output;
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
		Date date = Calendar.getInstance().getTime();
		
		try{
			output = new DataOutputStream(client.getSock().getOutputStream());
			output.writeUTF(client.getSock().getInetAddress().toString()+
					        ":"+client.getSock().getPort()+
					        "/~"+this.fromName+": "+
					       this.msg+" "+sdfTime.format(date)+" "+
					       sdfDate.format(date)+" (PVT)");
		}catch (IOException e){}
	}

}
