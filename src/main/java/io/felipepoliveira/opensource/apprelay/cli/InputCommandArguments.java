package io.felipepoliveira.opensource.apprelay.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.felipepoliveira.opensource.apprelay.Assert;

/**
 * This class store the input arguments sent by the client
 * @author Felipe Oliveira
 */
public class InputCommandArguments {
	
	/**
	 * Store the command name. The command name is the first argument of a command sent by the user
	 */
	private String commandName;
	
	/**
	 * Store the command flags. An flag is an argument identified by '--', for example: --test
	 */
	private Set<String> flags;
	
	/**
	 * Store all parameters pairs with name and value. For example -anparameter anvalue
	 */
	private HashMap<String, String> parameters;
	
	/**
	 * Store the arguments passed in the command parameter. For example: command arg1 arg2 arg3
	 */
	private List<String> arguments;
	
	/**
	 * Store the arguments used to build this object
	 */
	private String [] rawArguments;
	
	/**
	 * Store all argument scopes
	 * @author Felipe Oliveira
	 */
	private enum ArgumentsScopes {
		NAME,
		FLAG,
		ARGUMENT,
		PARAMETER_NAME,
		PARAMETER_VALUE,
	}
	
	public InputCommandArguments(String rawUserCommand) {
		this(rawUserCommand.split(" "));
	}
	
	/**
	 * Create an instance of {@link InputCommandArguments}. This class stores
	 * input data of a raw command sent by the client.<br/>
	 * <b>Example:</b><br/>
	 * <code></code>
	 * 
	 * @param commandArguments
	 */
	public InputCommandArguments(String[] commandArguments) {
		
		// Validate the given data
		Assert.is(commandArguments != null, "Null command arguments given");
		
		// Initialize all classes collections
		this.flags = new HashSet<String>();
		this.parameters = new HashMap<String, String>();
		this.arguments = new ArrayList<String>(commandArguments.length);
		this.rawArguments = commandArguments;
		this.commandName = "";
	
		// Store the current scope
		ArgumentsScopes currentScope = null;
		String lastParameterName = null;
		
		// Store if is the first argument
		boolean isFirstArgument = true;
		
		// Get each argument
		for (var arg : commandArguments) {
			
			// Check if it is the first argument to store the argument name
			if (isFirstArgument) {
				currentScope = ArgumentsScopes.NAME;
			}			
			// verify if it is on a flag 
			else if (arg.startsWith("--")) {
				currentScope = ArgumentsScopes.FLAG;
			}
			// verify if it is on an parameter name
			else if (arg.startsWith("-")) {
				currentScope = ArgumentsScopes.PARAMETER_NAME;	
			}
			// As an argument does not have an identifier it can only be set
			// if the current scope is not an parameter value
			else if (currentScope != ArgumentsScopes.PARAMETER_VALUE) {
				currentScope = ArgumentsScopes.ARGUMENT;
			}
			
			// Validate the scope
			switch (currentScope) {
			case NAME:
				this.commandName = arg;
				isFirstArgument = false;
				break;
			case FLAG:
				var flag = arg.substring(2);
				if (flag.length() == 0) {
					throw new IllegalArgumentException("Invalid flag. Empty flag '--'.");
				}	
				this.flags.add(flag);
				break;
			case ARGUMENT:
				this.arguments.add(new String(arg));
				break;
			case PARAMETER_NAME:
				lastParameterName = arg.substring(1);
				if (lastParameterName.length() == 0) {
					throw new IllegalArgumentException("Invalid parameter name. Empty parameter name '-'.");
				}
				currentScope = ArgumentsScopes.PARAMETER_VALUE;
				break;
			case PARAMETER_VALUE:
				this.parameters.put(lastParameterName, arg);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + currentScope);
			}		
		}
	}
	
	/**
	 * Return an boolean indicating if the arguments has an specific flag
	 * @param flagName
	 * @return
	 */
	public boolean hasFlag(String flagName) {
		return flags.contains(flagName);
	}
	
	public String getCommandName() {
		return commandName;
	}
	public Set<String> getFlags() {
		return flags;
	}
	public HashMap<String, String> getParameters() {
		return parameters;
	}
	
	public List<String> getArguments() {
		return arguments;
	}
	
	public String[] getRawArguments() {
		return rawArguments;
	}

	@Override
	public String toString() {
		return "InputCommandArguments [commandName=" + commandName + ", flags=" + flags + ", parameters=" + parameters
				+ "]";
	}

}
