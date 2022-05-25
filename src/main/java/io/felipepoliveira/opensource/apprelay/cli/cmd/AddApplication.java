package io.felipepoliveira.opensource.apprelay.cli.cmd;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.app.models.Application;
import io.felipepoliveira.opensource.apprelay.app.services.ApplicationService;
import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;
import io.felipepoliveira.opensource.apprelay.utils.FileUtils;

@Component
public class AddApplication extends Command{
	
	@Autowired
	private ApplicationService applicationService;
	
	@Override
	public String getName() {
		return "add";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws FileNotFoundException {
		// validate arguments
		if (commandArguments.getArguments().size() < 1) {
			System.err.println("This command expects at least 1 argument: The directory/file containg the configuration of the app to import");
			return;
		}
		
		// get path or file
		var fileOrDir = new File(commandArguments.getArguments().get(0));
		
		// Store the data of the application that will be added
		Application app = null;
		
		// if it is a file try to read it
		if (fileOrDir.isFile()) {
			app = Application.fromYaml(fileOrDir);
		}
		// if it is a directory
		else if (fileOrDir.isDirectory()) {
			var yamlFileFoundInDirectory = FileUtils.findFileInFolderByNames(fileOrDir.getAbsolutePath(), "apprelay.yaml", "apprelay.yml");
			
			// check if any configuration file was found
			if (yamlFileFoundInDirectory.isEmpty()) {
				System.err.println(
						String.format(
								"There is no AppRelay configuration file on the directory: %s. "
								+ "Create an apprelay.yaml or apprelay.yml file",
								fileOrDir.getAbsolutePath()));
				return;
			}
			
			app = Application.fromYaml(yamlFileFoundInDirectory.get());
		}
		else {
			System.err.println("Invalid directory or file given: " + commandArguments.getArguments().get(0));
			return;
		}
		
		// Add the application
		applicationService.addApplication(app, commandArguments.hasFlag("replace"));
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Add an program into the App Relay database";
	}

}
