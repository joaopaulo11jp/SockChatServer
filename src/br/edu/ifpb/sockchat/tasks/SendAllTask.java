package br.edu.ifpb.sockchat.tasks;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import br.edu.ifpb.sockchat.connectionBehavior.ClientConn;
import br.edu.ifpb.sockchat.connectionBehavior.ConnectionMap;

public class SendAllTask implements Task{
	private String fromName;
    private String msg;
	
    public SendAllTask(String fromName, String msg) {
		this.fromName = fromName;
		this.msg = msg;
	}
    
	@Override
	public void doTask() {
		Collection<ClientConn> allClients = ConnectionMap.getInstance().getAllClient();
		ClientConn client;
		DataOutputStream output;
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/mm/yyyy");
		Date date = Calendar.getInstance().getTime();
		
		Iterator<ClientConn> it = allClients.iterator();
		try{
			while(it.hasNext()){
				client = it.next();
				output = new DataOutputStream(client.getSock().getOutputStream());
				output.writeUTF(client.getSock().getInetAddress().toString()+
						        ":"+client.getSock().getPort()+
						        "/~"+this.fromName+": "+
						       this.msg+" "+sdfTime.format(date)+" "+
						       sdfDate.format(date));
			}
		}catch (IOException e){}
	}

}
