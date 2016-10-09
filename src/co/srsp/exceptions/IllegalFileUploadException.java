package co.srsp.exceptions;

import java.io.Serializable;

public class IllegalFileUploadException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2962989957665839912L;

	public IllegalFileUploadException(String message){
		super(message);
	}
}
