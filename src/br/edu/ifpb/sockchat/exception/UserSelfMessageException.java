package br.edu.ifpb.sockchat.exception;

public class UserSelfMessageException extends Exception{

	public UserSelfMessageException(){
		super("You can't send message to yourself!");
	}
}
