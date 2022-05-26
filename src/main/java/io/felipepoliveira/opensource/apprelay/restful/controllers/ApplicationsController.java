package io.felipepoliveira.opensource.apprelay.restful.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.felipepoliveira.opensource.apprelay.app.excp.BindingResultException;
import io.felipepoliveira.opensource.apprelay.app.excp.NotFoundException;
import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;
import io.felipepoliveira.opensource.apprelay.restful.dto.ExecuteApplicationDTO;

@RestController
@RequestMapping("/rest/v1/apps")
public class ApplicationsController {
	
	@Autowired
	private ApplicationService applicationService;
	
	@PostMapping("/{appName}/execute")
	public ResponseEntity<?> executeAsSubprocess(
			@PathVariable String appName,
			@RequestBody @Valid ExecuteApplicationDTO dto,
			BindingResult bindingResult) throws BindingResultException, NotFoundException {
		
		// thrown exception if binding result has errors
		if (bindingResult.hasErrors()) {
			throw new BindingResultException(bindingResult);
		}
		
		// execute the application
		var response = applicationService.execute(appName, dto.getArgs());
		
		// return the response to the client
		return ResponseEntity.ok(response);	
	}
	
	/**
	 * Return all registered on the AppRelay instance
	 * @return
	 */
	@GetMapping
	public ResponseEntity<?> listRegisteredApps() {
		return ResponseEntity.ok(applicationService.findAll());
	}

}
