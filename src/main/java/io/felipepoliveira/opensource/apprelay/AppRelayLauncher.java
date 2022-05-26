package io.felipepoliveira.opensource.apprelay;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import io.felipepoliveira.opensource.apprelay.cli.CommandsMapper;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;
import io.felipepoliveira.opensource.apprelay.cli.cmd.Command;

/**
 * This class initialize the command line interface
 * @author Felipe Oliveira
 *
 */
@Configuration
@ComponentScans(
		{
			@ComponentScan("io.felipepoliveira.opensource.apprelay.app"),
			@ComponentScan("io.felipepoliveira.opensource.apprelay.cli"),
		}
)
public class AppRelayLauncher {
	
	private static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	
	private static void runDependencyInjection() {
		// startup spring dependencies
		ctx.register(AppRelayLauncher.class);
		ctx.refresh();
		
		// map all registered commands
		var registeredCommands = ctx.getBeansOfType(Command.class);
		for (var command : registeredCommands.values()) {
			CommandsMapper.register(command);
		}
		
		// close the application context
		ctx.close();
	}
	
	public static void main(String[] args) {
		runDependencyInjection();
		
		// Scanner used to read client data
		Scanner sc = new Scanner(System.in);
	
		// Marks the end of the application execution
		boolean receiveInputEnabled = true;
		
		// Application main loop
		while (receiveInputEnabled) {
			
			// Get the command input arguments
			InputCommandArguments cmdInputArgs = null;
			try {
				// read the user input only if the application do not receive any arguments
				if (args.length == 0) {
					System.out.print("ar:> ");
					var userInput = sc.nextLine().trim();
					
					// ignore command if user send an empty string
					if (userInput.isEmpty()) {
						continue;
					}
					cmdInputArgs = new InputCommandArguments(userInput);
				}
				else {
					cmdInputArgs = new InputCommandArguments(args);
					receiveInputEnabled = false;
				}
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
			
			// Query the command by its name
			var qCmd = CommandsMapper.findCommand(cmdInputArgs.getCommandName());
			if (qCmd.isEmpty()) {
				System.out.println(String.format("Command '%s' not found", cmdInputArgs.getCommandName()));
				continue;
			}
			
			// Get the command
			var cmd = qCmd.get();
			
			// Check if the user asks for help
			if (cmdInputArgs.hasFlag("help")) {
				cmd.printHelpTextOnStdout();
				continue;
			}
			
			//Otherwise execute the command
			try {
				cmd.execute(cmdInputArgs);
			} catch (Exception e) {
				System.err.println("An error occur while executing command:");
				e.printStackTrace();
			}
		}
		
		// Close the scanner
		sc.close();
	}

}
