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
			System.out.println(String.format("--[START] Executing:%s", String.join(" ", processArgs)));
			//String inputString = "";
			StringBuilder processInputStreamText = new StringBuilder();
			var process = processBuilder.start();
			
			// async thread to read process input stream
			// this feature is important on tests on windows platform where the
			// process was "never finishing" because it was outputing some data
			// on the input stream and this client was never consuming it.
			// 
			// this thread will consume the entire input stream until the process finishes 
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					do {
						try {
							byte[] inputStream = process.getInputStream().readAllBytes();
							processInputStreamText.append(new String(inputStream, StandardCharsets.UTF_8));
						} catch (IOException e) {}
					} while (process.isAlive());
					
				}
			}).start();
			
			// Ask for the current thread to wait for the process to finish
			if (getTimeout() > 0) {
				process.waitFor(this.getTimeout(), TimeUnit.MILLISECONDS);
			}
			else {				
				process.waitFor();
				
			}
			
			// read possible remaining stream data
			byte[] inputStream = process.getInputStream().readAllBytes();
			processInputStreamText.append(new String(inputStream, StandardCharsets.UTF_8));
			
			// read the output from the application
			System.out.print(processInputStreamText.toString());
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
