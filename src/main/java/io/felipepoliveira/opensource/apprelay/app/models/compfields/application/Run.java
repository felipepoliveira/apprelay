package io.felipepoliveira.opensource.apprelay.app.models.compfields.application;

import java.io.Serializable;

import io.felipepoliveira.opensource.apprelay.app.functions.FunctionExecutor;

public class Run implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FunctionExecutor event;
	
	public FunctionExecutor getEvent() {
		return event;
	}
	
	public void setEvent(FunctionExecutor event) {
		this.event = event;
	}

}
