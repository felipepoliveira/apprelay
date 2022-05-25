package io.felipepoliveira.opensource.apprelay.utils;

import java.io.File;
import java.util.Optional;

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

}
