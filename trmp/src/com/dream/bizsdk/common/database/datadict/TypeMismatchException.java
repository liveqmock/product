package com.dream.bizsdk.common.database.datadict;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class TypeMismatchException extends Exception {
    String paramName;
    String typeWanted;
    String typeActual;

    public TypeMismatchException() {
    }

    public TypeMismatchException(String paramName, String typeWanted
                                 , String typeActual) {
        this.paramName = new String(paramName);
        this.typeActual = new String(typeActual);
        this.typeWanted = new String(typeWanted);
    }

    public String getMessage() {
        String msg;
        msg = "The actual type of parameter '" + paramName + "' is '" + typeActual + "',but";
        msg += " what expected is '" + typeWanted + "'!";
        return msg;
    }
}