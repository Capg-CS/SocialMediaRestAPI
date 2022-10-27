package com.exception;

public class NoForumFoundException extends Exception{
	  public NoForumFoundException(String str) 
	  {
		  super(str);
	  }
	  @Override
	  public String toString() {
		  return "No Forum found";
	  }
	}
