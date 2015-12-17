package br.edu.ifpb.sockchat.exception;

public class UserIsNotConnectedException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserIsNotConnectedException(String userName){
		super(userName+" is not connected");
	}
}
