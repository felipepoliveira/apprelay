package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class ExecuteApplication extends Command{
	
	@Autowired
	private ApplicationService applicationService;

	@Override
	public String getName() {
		return "exec";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		
		// validate the app name
		if (commandArguments.getRawArguments().length < 2) {
			System.err.println("Expected the name of the application");
			return;
		}
		
		// get the app by its name
		var appName = commandArguments.getRawArguments()[1];
		
		
		String[] appArgs = new String[commandArguments.getRawArguments().length - 2];
		for (int i = 0; i < appArgs.length; i++) {
			appArgs[i] = commandArguments.getRawArguments()[i + 2];
		}
		
		// Execute the application
		applicationService.execute(appName, appArgs);
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Execute a registered application 'run' routine";
	}

}
