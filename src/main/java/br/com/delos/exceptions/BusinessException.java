package br.com.delos.exceptions;

import java.util.List;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -8972609392275726092L;
	private final List<String> messageList;
	private final String message;
	
	public BusinessException(String message){
		super(message);
		this.message = message;
		this.messageList = null;
	}
	
	public BusinessException(List<String> mensagemList){
		super();
		StringBuilder sb = new StringBuilder();
		for (String msg : mensagemList) {
			sb.append(msg + "; ");
		}
		this.message = sb.toString();
		this.messageList = mensagemList;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

	public List<String> getMessageList() {
		return messageList;
	}
}
