package io.felipepoliveira.opensource.apprelay.restful.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.felipepoliveira.opensource.apprelay.app.excp.BindingResultException;
import io.felipepoliveira.opensource.apprelay.app.excp.FunctionalityRuleException;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ExceptionHandler({BindingResultException.class})
	protected ResponseEntity<?> handleBindingResultException(BindingResultException e) {
		
		// Get the error message from the exception
		String message = e.getMessage();
		if (message == null) {
			message = "An unexpected error occurred";
		}
		else if (message.length() > 255) {
			message = message.substring(0, 255);
		}
		
		// Build the error body based on the binding result errors
		Map<String, String> errorsMap = new HashMap<String, String>();
		for (var error : e.getBindingResult().getFieldErrors()) {
			errorsMap.put(error.getField(), error.getDefaultMessage());
		}
		
		return ResponseEntity
				.status(e.asHttpStatusCode())
				.header("X-Reason", message)
				.body(errorsMap);
		
	}
	
	@ExceptionHandler({FunctionalityRuleException.class})
	protected ResponseEntity<?> handleFunctionalityRuleException(FunctionalityRuleException e) {
		
		// Get the error message from the exception
		String message = e.getMessage();
		if (message == null) {
			message = "An unexpected error occurred";
		}
		else if (message.length() > 255) {
			message = message.substring(0, 255);
		}
		
		return ResponseEntity
				.status(e.asHttpStatusCode())
				.header("X-Reason", message)
				.build();
		
	}

}
