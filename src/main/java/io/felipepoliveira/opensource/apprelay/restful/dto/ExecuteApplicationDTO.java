package io.felipepoliveira.opensource.apprelay.restful.dto;

import javax.validation.constraints.NotNull;

public class ExecuteApplicationDTO {
	
	@NotNull
	private String args[];
	
	public String[] getArgs() {
		return args;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}

}
