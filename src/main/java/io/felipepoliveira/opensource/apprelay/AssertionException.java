package io.felipepoliveira.opensource.apprelay;

/**
 * Exception used in data assertions
 * @author Felipe Oliveira
 */
@SuppressWarnings("serial")
public class AssertionException extends RuntimeException {

	public AssertionException(String message) {
		super(message);
	}
	
}