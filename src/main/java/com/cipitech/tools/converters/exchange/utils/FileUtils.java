package com.cipitech.tools.converters.exchange.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Utility functions regarding files.
 */
public class FileUtils
{
	/**
	 * Read a file and get the bytes
	 *
	 * @param fileName the absolute file path
	 * @return a byte array representation of the file
	 */
	public static byte[] getFileBytes(String fileName) throws IOException
	{
		File file = new File(fileName);
		return Files.readAllBytes(file.toPath());
	}
}
