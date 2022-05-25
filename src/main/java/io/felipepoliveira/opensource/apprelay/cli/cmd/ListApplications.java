package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.db.ApplicationDB;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;

@Component
public class ListApplications extends Command{
	
	@Autowired
	private ApplicationDB applicationDB;

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		for (var app : applicationDB.fetchAll()) {
			System.out.println(app.getName());
		}
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "list all registered apps";
	}

}
