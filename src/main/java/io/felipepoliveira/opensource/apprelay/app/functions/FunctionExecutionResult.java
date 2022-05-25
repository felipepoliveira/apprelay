package io.felipepoliveira.opensource.apprelay.app.functions;

public abstract class FunctionExecutionResult {
	
	/**
	 * Cast this instance as {@link ExecuteWithSubProcessResult}. If this class is not a instance of {@link ExecuteWithSubProcessResult}
	 * this method will thrown {@link ClassCastException}.
	 * @return
	 */
	public ExecuteWithSubProcessResult asExecuteWithSubProcess() {
		return (ExecuteWithSubProcessResult) this;
	}

}
