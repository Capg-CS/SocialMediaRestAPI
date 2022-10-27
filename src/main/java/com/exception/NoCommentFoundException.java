package com.exception;

public class NoCommentFoundException extends Exception{
    
    public NoCommentFoundException(String str) {
        // TODO Auto-generated constructor stub
        super(str);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "No Comment found";
    }



}