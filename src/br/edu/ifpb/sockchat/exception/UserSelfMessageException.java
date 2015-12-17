package br.edu.ifpb.sockchat.exception;

public class UserSelfMessageException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserSelfMessageException(){
		super("You can't send message to yourself!");
	}
}
