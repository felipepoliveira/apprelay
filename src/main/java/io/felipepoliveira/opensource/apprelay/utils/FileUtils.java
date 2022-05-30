package io.felipepoliveira.opensource.apprelay.utils;

import java.io.File;
import java.util.Optional;

import io.felipepoliveira.opensource.apprelay.Debug;

public final class FileUtils {
	
	/**
	 * Return any {@link File} that exists on the given directory path identified by its name
	 * @param directory
	 * @param fileNames
	 * @return
	 */
	public static Optional<File> findFileInFolderByNames(String directory, String... fileNames) {
		
		for (String fileName : fileNames) {
			var file = new File(directory + File.separator + fileName);
			if (file.exists()) {
				return Optional.of(file);
			}
		}
		
		return Optional.empty();
	}
	
	/**
	 * Return an {@link File} file or directory relative to the classpath execution directory
	 * @param filepath - Should be a relative filepath, like "/adir", "./adir" or "../adir";
	 * @return
	 */
	public static File getFileRelativeToJarLocation(String filepath) {
		var relativeFile = new File(JarUtils.getJarExecutionDirectory().getAbsolutePath() + "\\" +  filepath);
		Debug.errPrintln("getFileRelativeToJarLocation {");
		Debug.errPrintln("	Given relative path: " + filepath);
		Debug.errPrintln("	Absolute path: " + relativeFile.getAbsolutePath());
		Debug.errPrintln("}");
		return relativeFile;
	}

}
