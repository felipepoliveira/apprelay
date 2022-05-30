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
	
	/**
	 * Store the application context
	 */
	private static ApplicationContexts context = ApplicationContexts.PRODUCTION;
	
	/**
	 * Return the current state of the application context
	 * @return
	 */
	public static ApplicationContexts getContext() {
		return context;
	}
	
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
		
		// read the input arguments from main method
		InputCommandArguments cmdInputArgs = new InputCommandArguments(args);;
		
		/*
		 *To let the application know that it is in DEBUG mode it checks if the --debug flag 
		 *was passed in the application arguments or if '--debug' was passed as the first 
		 *argument (making it commandName) 
		 */
		if (cmdInputArgs.hasFlag("debug") || cmdInputArgs.getCommandName().equals("--debug")) {
			System.err.println("Application running on DEBUG mode");
			context = ApplicationContexts.DEBUG;
		}
		
		// Application main loop
		while (receiveInputEnabled) {
			
			// Get the command input arguments
			try {
				// Only starts the CLI loop if the there is not arguments on the application or is on DEBUG mode
				if (args.length == 0 || context == ApplicationContexts.DEBUG) {
					System.out.print("ar:> ");
					var userInput = sc.nextLine().trim();
					
					// ignore command if user send an empty string
					if (userInput.isEmpty()) {
						continue;
					}
					cmdInputArgs = new InputCommandArguments(userInput);
				}
				else {
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
