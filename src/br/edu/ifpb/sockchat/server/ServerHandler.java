package br.edu.ifpb.sockchat.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.ifpb.sockchat.connectionBehavior.ClientHandler;

public class ServerHandler {
	private static ExecutorService threads = Executors.newCachedThreadPool();
	private static final int PORTA = 43594;
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		try{
			ServerSocket serverSock = new ServerSocket(PORTA);
			System.out.println("Server started!");
			//TODO Load the application before accept
			while(true){
				System.out.println("Waiting for a new connection...");
				Socket clientSock = serverSock.accept();
				threads.submit(new ClientHandler(clientSock));
				System.out.println("New connection received!");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
