package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class Exit extends Command{

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) {
		System.exit(-1);
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Close the CLI with exit code -1";
	}

}
