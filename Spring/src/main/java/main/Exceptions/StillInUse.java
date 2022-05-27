package main.Exceptions;

public class StillInUse extends RuntimeException{
    public StillInUse(String message){
        super(message);
    }
}