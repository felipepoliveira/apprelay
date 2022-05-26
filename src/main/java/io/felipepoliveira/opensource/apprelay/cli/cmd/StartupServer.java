package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;
import io.felipepoliveira.opensource.apprelay.restful.RestfulServerLauncher;

@Component
public class StartupServer extends Command{

	@Override
	public String getName() {
		return "startup-server";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		RestfulServerLauncher.launchServer(commandArguments.getRawArguments());
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Startup the RESTFul API";
	}

}
