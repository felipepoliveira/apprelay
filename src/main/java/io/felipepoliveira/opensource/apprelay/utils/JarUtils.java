package io.felipepoliveira.opensource.apprelay.utils;

import java.io.File;
import java.net.URISyntaxException;

public class JarUtils {
	
	/**
	 * Return the execution directory where this application is running
	 * @return
	 */
	public static File getJarExecutionDirectory() {
		try {
			return new File(JarUtils.class.getProtectionDomain().getCodeSource().getLocation()
				    .toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
