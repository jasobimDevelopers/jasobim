package com.my.spring.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

public class CopyFilesExample {
/*
 * Time taken by FileStreams Copy1 = 48884695

 *	Time taken by FileChannels Copy2 = 30350628

 *	Time taken by Java7 Files Copy3 = 25314932

 *	Time taken by Apache Commons IO Copy4 = 121575515
 * 
 * */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		File source = new File("E:\\JasoBim\\BimAppDocument\\tomcat_xyx_8080\\webapps\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\jasobim\\sb.html\\bb06fc1e1837e7927de52752c02e9e43.html");
		//                      E:\JasoBim\BimAppDocument\tomcat_xyx_8080\webapps\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\jasobim\floders\floders_1\floders_3\sb(1).html
		File dest = new File("C:\\Users\\Han\\Desktop\\oldFiles\\testcopy.html");
		//File source = new File("E:\\JasoBim\\BimAppDocument\\tomcat_xyx_8080\\webapps\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\jasobim\\sb.html\\bb06fc1e1837e7927de52752c02e9e43.html");
		//File dest = new File("C:\\Users\\Han\\Desktop\\copyFiles\\test.html");
		long end;
	/*	long start = System.nanoTime();
		// copy file using FileStreamslong start = System.nanoTime();

		copyFileUsingFileStreams(source, dest);
		System.out.println("Time taken by FileStreams Copy = "
				+ (System.nanoTime() - start));*/

		// copy files using java.nio.FileChannelsource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile2.txt");
		long start1 = System.nanoTime();
		copyFileUsingFileChannels(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by FileChannels Copy = " + (end - start1));

		/*// copy file using Java 7 Files classsource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile3.txt");
		long start2 = System.nanoTime();
		copyFileUsingJava7Files(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by Java7 Files Copy = " + (end - start2));

		// copy files using apache commons iosource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile4.txt");
		long start3 = System.nanoTime();
		copyFileUsingApacheCommonsIO(source, dest);
		end = System.nanoTime();
		System.out.println("Time taken by Apache Commons IO Copy = "
				+ (end - start3));*/

	}

	public static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	public static void copyFileUsingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public static void copyFileUsingJava7Files(File source, File dest)
			throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}

	public static void copyFileUsingApacheCommonsIO(File source, File dest)
			throws IOException {
		FileUtils.copyFile(source, dest);
	}

}
