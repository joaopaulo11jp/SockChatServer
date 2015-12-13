package br.edu.ifpb.sockchat.exception;

public class NameAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;

	public NameAlreadyExistsException(){
		super("Nome já existente!");
	}
}
