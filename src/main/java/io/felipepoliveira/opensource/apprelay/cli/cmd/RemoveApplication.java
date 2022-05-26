package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class RemoveApplication extends Command{
	
	@Autowired
	private ApplicationService applicationService;

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		// Check if the argument is required
		if (commandArguments.getArguments().size() < 1) {
			System.err.println("The application name is required");
			return;
		}
		
		var appName = commandArguments.getArguments().get(0);
		applicationService.removeApp(appName);
	}

	@Override
	public String getHelpText() {
		return getCommandSummary() + ". Use remove <appName> to delete an registered app";
	}

	@Override
	public String getCommandSummary() {
		return "Delete an registered app from the database";
	}

}
