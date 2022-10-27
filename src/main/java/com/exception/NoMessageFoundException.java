package com.exception;

public class NoMessageFoundException extends Exception{

	public NoMessageFoundException(String str) {
		// TODO Auto-generated constructor stub
		super(str);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "No messages found";
	}
}
