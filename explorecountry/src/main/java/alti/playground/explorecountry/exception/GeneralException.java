package alti.playground.explorecountry.exception;

public class GeneralException extends RuntimeException{

    private  String customMessage;

    public GeneralException(String erroMessage,String customMessage){
        super(erroMessage);
        this.customMessage = customMessage;
    }

    public GeneralException(String customMessage){
        super(customMessage);
        this.customMessage = customMessage;
    }


    public String getCustomMessage() {
        return customMessage;
    }
}
