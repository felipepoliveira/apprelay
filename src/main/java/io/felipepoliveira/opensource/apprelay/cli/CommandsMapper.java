package io.felipepoliveira.opensource.apprelay.cli;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.felipepoliveira.opensource.apprelay.Assert;
import io.felipepoliveira.opensource.apprelay.cli.cmd.Command;

public final class CommandsMapper {
	
	/**
	 * Store the commands
	 */
	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	/**
	 * Return the current registered commands
	 * @return
	 */
	public static Collection<Command> getRegisteredCommands() {
		return commands.values();
	}
	
	/**
	 * Normalize an key
	 * @param rawKey
	 */
	private static String normalizeCommandKey(String rawKey) {
		return rawKey.toLowerCase();
	}
	
	/**
	 * Register an command into the command mapper
	 * @param cmd
	 */
	public static void register(Command cmd) {
		// Assert data
		Assert.is(cmd != null, "Command can not be null");
		Assert.is(	cmd.getName() != null &&
					cmd.getName().matches("^\\w+$"), 
					String.format(
							"Invalid command %s. A command name can only contain letters, numbers and underscore [a-zA-Z0-9_]", 
							cmd.getName()
							)
					);
		
		// Normalize the key
		var key = normalizeCommandKey(cmd.getName());
		if (commands.containsKey(key)) {
			var alreadyIncludedCommand = commands.get(key);
			throw new IllegalArgumentException(
					String.format(
							"There is already a command with name %s from class %s", 
							cmd.getName(), alreadyIncludedCommand.getClass().getName()
							)
					);
		}
		
		// Include the command on the command map
		commands.put(normalizeCommandKey(cmd.getName()), cmd);
	}
	
	/**
	 * Return an {@link Optional} containing an command identified by its name.
	 * @param commandName
	 * @return
	 */
	public static Optional<Command> findCommand(String commandName) {
		return Optional.ofNullable(commands.get(normalizeCommandKey(commandName)));
	}
	

}
