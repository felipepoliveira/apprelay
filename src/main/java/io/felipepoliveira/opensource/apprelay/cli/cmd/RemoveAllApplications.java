package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class RemoveAllApplications extends Command{
	
	@Autowired
	private ApplicationService applicationService;

	@Override
	public String getName() {
		return "remove-all";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		applicationService.removeAll();
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Remove all registered apps from this AppRelay instance";
	}

}
