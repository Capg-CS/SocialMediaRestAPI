package com.exception;

public class NoLoggedUserFoundException extends Exception  {

    public NoLoggedUserFoundException(String str) {
        // TODO Auto-generated constructor stub
        super(str);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "No user found";
    }

}
