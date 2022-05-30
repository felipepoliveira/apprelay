package io.felipepoliveira.opensource.apprelay.cli.cmd;

import org.springframework.stereotype.Component;

import io.felipepoliveira.opensource.apprelay.cli.InputCommandArguments;
import io.felipepoliveira.opensource.apprelay.utils.JarUtils;

@Component
public class WhereAmI extends Command{

	@Override
	public String getName() {
		return "whereami";
	}

	@Override
	public void execute(InputCommandArguments commandArguments) throws Exception {
		System.out.println(JarUtils.getJarExecutionDirectory().getAbsolutePath());
	}

	@Override
	public String getHelpText() {
		return getCommandSummary();
	}

	@Override
	public String getCommandSummary() {
		return "Show on default output in witch directory this application is running";
	}

}
