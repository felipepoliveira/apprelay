package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.cli.CommandsMapper;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class Help extends Command {

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) {
		System.out.println(getHelpText());
	}
	
	@Override
	public String getCommandSummary() {
		return "Display help information about the AppRelay CLI";
	}

	@Override
	public String getHelpText() {
		var helpText = new StringBuilder();
		helpText.append("In the AppRelay CLI you can use:" + System.lineSeparator());
		for (var cmd : CommandsMapper.getRegisteredCommands()) {
			helpText.append(cmd.getName() + " - \t");
			helpText.append(cmd.getCommandSummary());
			helpText.append(System.lineSeparator());
		}
		helpText.append("Use any command with <command> --help flag to display a help text");
		helpText.append(System.lineSeparator());
		
		return helpText.toString();
	}

}
