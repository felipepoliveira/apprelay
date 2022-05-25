package io.felipepoliveira.opensource.apprelay.app.extconf.yaml;

import java.util.Set;

import io.felipepoliveira.opensource.apprelay.Assert;
import io.felipepoliveira.opensource.apprelay.app.functions.ExecuteWithSubProcess;
import io.felipepoliveira.opensource.apprelay.app.functions.FunctionExecutor;

public class ExecuteConfiguration {
	
	private String cmd;
	
	private Set<Integer> expectedExitCodes;
	
	private long timeout;
	
	/**
	 * Create an instance of a {@link FunctionExecutor} based on the data collected from the yaml configuration file
	 * @return
	 */
	public FunctionExecutor createFunctionExecutorInstance(String appDirectory) {
		Assert.is(this.cmd != null, "Parameter 'cmd' is required for 'execute' configuration");
		
		var executor = new ExecuteWithSubProcess();
		executor.setAppDirectory(appDirectory);
		executor.setCommand(cmd);
		executor.setExpectedExitCodes(expectedExitCodes);
		executor.setTimeout(timeout);
		
		return executor;
		
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Set<Integer> getExpectedExitCodes() {
		return expectedExitCodes;
	}
	
	public void setExpectedExitCodes(Set<Integer> expectedExitCodes) {
		this.expectedExitCodes = expectedExitCodes;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	

}
