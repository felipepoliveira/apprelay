package io.felipepoliveira.opensource.apprelay.app.models.compfields.application;

import java.io.Serializable;
import java.util.Collection;

import io.felipepoliveira.opensource.apprelay.app.functions.FunctionExecutor;

public class OnLoad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<FunctionExecutor> events;
	
	public Collection<FunctionExecutor> getEvents() {
		return events;
	}
	
	public void setEvents(Collection<FunctionExecutor> events) {
		this.events = events;
	}

}
