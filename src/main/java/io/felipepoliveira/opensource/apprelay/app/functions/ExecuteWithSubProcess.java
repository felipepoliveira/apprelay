package io.felipepoliveira.opensource.apprelay.app.functions;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.felipepoliveira.opensource.apprelay.utils.ArrayUtils;

public class ExecuteWithSubProcess extends FunctionExecutor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appDirectory;
	
	private String command;
	
	private Set<Integer> expectedExitCodes;
	
	public String getAppDirectory() {
		return appDirectory;
	}
	
	public void setAppDirectory(String appDirectory) {
		this.appDirectory = appDirectory;
	}

	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}

	public Set<Integer> getExpectedExitCodes() {
		return expectedExitCodes;
	}

	public void setExpectedExitCodes(Set<Integer> expectedExitCodes) {
		this.expectedExitCodes = expectedExitCodes;
	}
	
	private String replaceCommandWildcards(String cmd) {
		return cmd.replace("${localDir}", appDirectory);
	}
	
	public ProcessBuilder createOSTerminalProcess(String... command) {
		final String[] PREFIXED_WIN_COMMANDS = new String[] {"cmd.exe", "/C", "cd", appDirectory, "&&"};
		
		
		// if the current plataform is windows
		String[] cmd = new String[PREFIXED_WIN_COMMANDS.length +  command.length];
		for (int i = 0; i < PREFIXED_WIN_COMMANDS.length; i++) {
			cmd[i] = PREFIXED_WIN_COMMANDS[i];
		}
		for (int i = 0; i < command.length; i++) {
			cmd[i + PREFIXED_WIN_COMMANDS.length] = command[i];
		}
		return new ProcessBuilder(cmd);
	}
	
	@Override
	public FunctionExecutionResult execute(String[] externalArguments) {
		// concatenate the app directory with the command name
		var splittedCmds = this.command.split(" ");
		for (int i = 0; i < splittedCmds.length; i++) {
			splittedCmds[i] = replaceCommandWildcards(splittedCmds[i]);
		}
		
		// create the arguments array that will be passed to the process builder
		var processArgs = (externalArguments != null) ? (String[]) ArrayUtils.concat(splittedCmds, externalArguments) : splittedCmds;
		
		// create the process builder and start it
		var processBuilder = createOSTerminalProcess(processArgs);
		processBuilder.redirectErrorStream(true);
		try {
			
			// start and wait for the process to finish
			var process = processBuilder.start();
			if (getTimeout() > 0) {
				process.waitFor(this.getTimeout(), TimeUnit.MILLISECONDS);
			}
			else {
				process.waitFor();
			}
			
			// read the output from the application
			System.out.println(String.format("--[START] Executing:%s", String.join(" ", processArgs)));
			byte[] inputStream = process.getInputStream().readAllBytes();
			var inputString = new String(inputStream, StandardCharsets.UTF_8);
			System.out.print(inputString);
			System.out.println(String.format("--[END] Executing:%s", String.join(" ", processArgs)));
			
			// get the exit code and return it as execution response
			return new ExecuteWithSubProcessResult(process.exitValue());
			
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public boolean validate(FunctionExecutionResult result) {
		// ignore if the result is not a instance of execute with sub process
		if (!(result instanceof ExecuteWithSubProcessResult)) {
			return false;
		}
		
		// expected exit codes
		var expectedExitCodes = 
				(this.expectedExitCodes != null && this.expectedExitCodes.size() > 0) ?
					this.expectedExitCodes :
						new HashSet<Integer>(Arrays.asList(0));
		
		// check if has the expected exit code
		return expectedExitCodes.contains(result.asExecuteWithSubProcess().getExitCode());
						
	}	
}
