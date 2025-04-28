package model;

public class Result {
    private boolean isSuccessfull;
    private String message;

    public Result(boolean isSuccessfull, String message){
        this.isSuccessfull = isSuccessfull;
        this.message = message;
    }

    public void setMessge(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setIsSuccessfull(boolean success){
        this.isSuccessfull = success;
    }
    
    public boolean getIsSuccessfull(){
        return this.isSuccessfull;
    }
}
