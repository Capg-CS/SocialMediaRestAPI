package com.exception;

public class NoGroupFoundException extends Exception{
	  public NoGroupFoundException(String str) 
	  {
		  super(str);
	  }
	  @Override
	  public String toString() {
		  return "No group found";
	  }
	}
