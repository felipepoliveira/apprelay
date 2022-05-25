package io.felipepoliveira.opensource.apprelay.app.excp;

@SuppressWarnings("serial")
public abstract class FunctionalityRuleException extends Exception {
	
	/**
	 * Return the representation of this exception as an HTTP status code
	 * @return
	 */
	public abstract int asHttpStatusCode();

	public FunctionalityRuleException() {
		super();
	}

	public FunctionalityRuleException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FunctionalityRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public FunctionalityRuleException(String message) {
		super(message);
	}

	public FunctionalityRuleException(Throwable cause) {
		super(cause);
	}
	
	

}
