package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;
import io.felipepoliveira.opensource.apprelay.restful.RestfulServerLauncher;

@Component
public class StartRestfulServer extends Command{
	
	private boolean serverLoaded = false;

	@Override
	public String getName() {
		return "start-server";
	}
	
	

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		if (!serverLoaded) {
			RestfulServerLauncher.launchServer(commandArguments.getRawArguments());
			serverLoaded = true;
		}
		else {
			System.err.println("Server already running");
		}
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
