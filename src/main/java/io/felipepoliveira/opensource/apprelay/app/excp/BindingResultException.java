package io.felipepoliveira.opensource.apprelay.app.excp;

import org.springframework.validation.BindingResult;

@SuppressWarnings("serial")
public class BindingResultException extends FunctionalityRuleException{
	
	/**
	 * The binding result that originated the exception
	 */
	private BindingResult bindingResult;
	
	public BindingResultException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	
	public BindingResult getBindingResult() {
		return bindingResult;
	}

	@Override
	public int asHttpStatusCode() {
		return 422;
	}

}
