package com.rinku.electronic.store.ElectronicStore.exception;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message)
    {
    super(message);
    }
    public BadApiRequest()
    {
    super("Bad Request");
    }

}