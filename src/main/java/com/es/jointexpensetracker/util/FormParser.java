package com.es.jointexpensetracker.util;

public abstract class FormParser {
    private boolean isValid = true;
    private StringBuilder errorMessage;
    private String delimiter;

    public boolean isValid(){
        return isValid;
    }

    public String getErrorMessage(){
        return (errorMessage==null)? null : errorMessage.toString();
    }

    protected void setDelimiter(String delim){
        delimiter = delim;
    }

    protected void setErrorState(String message){
        setErrorState(message, true);
    }

    protected void setErrorState(String message, boolean append){
        if (message == null)
            throw new IllegalArgumentException("Error message mustn't be null if the state is not valid");
        isValid = false;
        if (append && errorMessage!=null){
            if (delimiter != null)
                errorMessage.append(delimiter);
            errorMessage.append(message);
        } else
            errorMessage = new StringBuilder(message);
    }
}
