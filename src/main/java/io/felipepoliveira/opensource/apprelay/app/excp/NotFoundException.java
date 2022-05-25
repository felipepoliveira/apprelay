package io.felipepoliveira.opensource.apprelay.app.excp;

@SuppressWarnings("serial")
public class NotFoundException extends FunctionalityRuleException{
	
	

	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	@Override
	public int asHttpStatusCode() {
		return 404;
	}

}
