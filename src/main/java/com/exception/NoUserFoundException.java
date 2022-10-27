package com.exception;

public class NoUserFoundException extends Exception{
	
	public NoUserFoundException(String str) {
		// TODO Auto-generated constructor stub
		super(str);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "No user found";
	}

}
