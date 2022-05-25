package io.felipepoliveira.opensource.apprelay.app.functions;

public class ExecuteWithSubProcessResult extends FunctionExecutionResult {
	
	private int exitCode;
	
	public ExecuteWithSubProcessResult(int exitCode) {
		super();
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}

}
