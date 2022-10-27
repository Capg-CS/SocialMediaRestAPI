package com.exception;

public class MessageCannotBeEmptyException extends Exception {
	
	public MessageCannotBeEmptyException(String str) {
		// TODO Auto-generated constructor stub
		super(str);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Message cannot be empty";
	}

}
