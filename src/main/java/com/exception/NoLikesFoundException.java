package com.exception;

public class NoLikesFoundException extends Exception{
    
    public NoLikesFoundException(String str) {
        // TODO Auto-generated constructor stub
        super(str);
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "No Likes found";
    }



}