package com.exception;

public class NoPostFoundException  extends Exception {

	
	public NoPostFoundException(String str) {
		// TODO Auto-generated constructor stub
		super(str);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "No messages found";
	}
}