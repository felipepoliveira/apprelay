package io.felipepoliveira.opensource.apprelay.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.felipepoliveira.opensource.apprelay.Debug;

public class JarUtils {
	
	/**
	 * Return the execution directory where this application is running
	 * @return
	 */
	public static File getJarExecutionDirectory() {
		Debug.errPrintln("getJarExecutionDirectory {");
		try {
			
			// get the application path
			String path = JarUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			Debug.errPrintln("	CodeSource.Location.Path:" + path);
			
			// use URL decoder to normalize the filepath 
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			Debug.errPrintln("	DECODED:CodeSource.Location.Path:" + decodedPath);
			
			// remove the .jar! folder if exists
			int index = -1;
			if ((index = decodedPath.lastIndexOf(".jar!")) >= 0) {
				decodedPath = decodedPath.substring(0, index);
				
				// remove the reference directory
				if ((index = decodedPath.lastIndexOf("/")) >= 0) {
					decodedPath = decodedPath.substring(0, index);
				}
			}
			
			// remove initial 'file:/' prefix
			final String URL_FILE_PREFIX = "file:/";
			if (decodedPath.startsWith(URL_FILE_PREFIX)) {
				decodedPath = decodedPath.substring(URL_FILE_PREFIX.length());
			}
			var directory = new File(decodedPath);
			Debug.errPrintln("	NORMALIZED:CodeSource.Location.Path:" + decodedPath);
			Debug.errPrintln("	File.getAbsolutePath:" + directory.getAbsolutePath());
			
			return directory;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} finally {
			Debug.errPrintln("}");
		}
	}

}
