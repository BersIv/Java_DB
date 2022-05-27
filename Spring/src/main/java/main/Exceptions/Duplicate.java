package main.Exceptions;

public class Duplicate extends RuntimeException{
    public Duplicate(String message){
        super(message);
    }
}