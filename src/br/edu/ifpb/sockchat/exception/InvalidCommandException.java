package br.edu.ifpb.sockchat.exception;

public class InvalidCommandException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidCommandException(){
		super("Comando Inválido!");
	}
}
