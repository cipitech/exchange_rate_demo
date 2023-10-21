package com.cipitech.tools.converters.exchange.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils
{
	public static byte[] getFileBytes(String fileName) throws IOException
	{
		File file = new File(fileName);
		return Files.readAllBytes(file.toPath());
	}
}
