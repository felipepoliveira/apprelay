package io.felipepoliveira.opensource.apprelay.app.functions;

public abstract class FunctionExecutor {
	
	/**
	 * Timeout in milliseconds used to execute an external software
	 */
	private long timeout;
	
	/**
	 * Execute the external software
	 */
	public abstract FunctionExecutionResult execute(String[] externalArguments);
	
	/**
	 * Validate an {@link FunctionExecutionResult}
	 * @param result
	 * @return
	 */
	public abstract boolean validate(FunctionExecutionResult result);
	
	public long getTimeout() {
		return timeout;
	}
	
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

}
