package io.felipepoliveira.opensource.apprelay.cli.cmd;

import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

public abstract class Command {
	
	/**
	 * Return the name of the command
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * Execute the command algorithm
	 * @param commandArguments
	 */
	public abstract void execute(InputCommandArguments commandArguments) throws Exception;

	/**
	 * Return the help text used by the platform
	 * @return
	 */
	public abstract String getHelpText();
	
	/**
	 * Return the command short summary
	 * @return
	 */
	public abstract String getCommandSummary();
	
	/**
	 * Print the command help text into the standard output system using <code>System.out.println</code>
	 */
	public void printHelpTextOnStdout() {
		System.out.println(getHelpText());
	}

}
