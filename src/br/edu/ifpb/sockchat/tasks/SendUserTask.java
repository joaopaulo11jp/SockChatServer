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
		ClientConn toClient = ConnectionMap.getInstance().getClient(toName);
		ClientConn fromClient = ConnectionMap.getInstance().getClient(fromName);
		DataOutputStream outputTo;
		DataOutputStream outputFrom;
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
		Date date = Calendar.getInstance().getTime();
		
		//TODO Possíveis erros : Usuário nao existir e Enviar para si mesmo
		try{
			String msg = toClient.getSock().getInetAddress().toString()+
			             ":"+toClient.getSock().getPort()+
			             "/~"+this.fromName+": "+
			             this.msg+" "+sdfTime.format(date)+" "+
			             sdfDate.format(date)+" (PVT";
			
			outputTo = new DataOutputStream(toClient.getSock().getOutputStream());
			outputTo.writeUTF(msg+" to you)");
			
			outputFrom = new DataOutputStream(fromClient.getSock().getOutputStream());
			outputFrom.writeUTF(msg+" to "+toClient.getName()+")");
		}catch (IOException e){}
	}

}
