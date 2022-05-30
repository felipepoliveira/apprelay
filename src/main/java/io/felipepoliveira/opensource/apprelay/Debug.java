package io.felipepoliveira.opensource.apprelay;

public class Debug {
	
	/**
	 * Call System.err.println only if the application is on DEBUG mode
	 * @param ouput
	 */
	public static void errPrintln(Object ouput) {
		if (AppRelayLauncher.getContext() == ApplicationContexts.DEBUG) {
			System.err.println("[DEBUG]: " + ouput);
		}
	}

}
