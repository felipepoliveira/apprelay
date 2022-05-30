package io.felipepoliveira.opensource.apprelay.restful.dto;

import io.felipepoliveira.opensource.apprelay.app.models.Application;

public class ApplicationSummaryDataDTO {
	
	private String name;
	
	public ApplicationSummaryDataDTO(Application app) {
		this.name = app.getName();
	}
	
	public String getName() {
		return name;
	}

}
