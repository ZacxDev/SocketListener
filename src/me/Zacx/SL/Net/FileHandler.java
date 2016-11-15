package me.Zacx.SL.Net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {

	
	public void appendFile(String fileName, String str) {
		
			
		Path current = Paths.get("");
		String path = current.toAbsolutePath().toString();
		File root = new File(path);
		File og = new File(path + "\\" + fileName + ".txt");
		File temp = new File(path + "\\" + fileName + ".temp");

			try {
				
				BufferedReader br = new BufferedReader(
						new FileReader(og));
				
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(temp));
				
				String line = br.readLine();
				
				if (line == null) {
					
					bw.write(str);
					bw.newLine();
					
				}
				
				while (line != null) {
					
					bw.write(line);
					bw.newLine();
					line = br.readLine();
					
					if (line == null) {
						
						bw.write(str);
						bw.newLine();
						break;
					}
					
				}
				
				
				
				bw.flush();
				bw.close();
				br.close();
				
				Files.delete(og.toPath());
				temp.renameTo(og);
				
			} catch(Exception e) {}
			
		}
}