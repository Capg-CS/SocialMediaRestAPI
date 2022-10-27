package com.exception;

public class NoAdminFoundException extends Exception {
	public NoAdminFoundException(String str) {
        // TODO Auto-generated constructor stub
        super(str);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "No admin found";
    }

}
