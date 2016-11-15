package me.Zacx.SL.Net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Core {

	private String line;
	private FileHandler fh;
	
	public Core() {
		fh = new FileHandler();
		System.out.println("Listening on port 4444...");
		try (
			ServerSocket serverSock = new ServerSocket(4444);
			Socket client = serverSock.accept();
			
			PrintWriter out =
			        new PrintWriter(client.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			        new InputStreamReader(client.getInputStream()));
		) {
			System.out.println("Connected");
			out.println("Connected");
		while ((line = in.readLine()) != null) {
			System.out.println("Client: " + line);
			out.println("response");
			
			if (line.startsWith("auth")) {
				String[] split = line.split(" ");
				out.println(auth(split[1].trim(), split[2].trim()));
			}
		}
		System.out.println("Closing Socket");
		out.close();
		in.close();
		client.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		new Core();
	}
	
	public boolean uidFound = false;

	public boolean auth(String uid, String mac) {
		
		Path current = Paths.get("");
		String path = current.toAbsolutePath().toString();
		File data = new File(path + "\\data.txt");
		
		try {
		BufferedReader br = new BufferedReader(new FileReader(data));
		line = br.readLine();
		
		while (line != null) {
			
			line = line.trim();
			
			if (line.contains(uid)) {
				uidFound = true;
				String fm = line.substring(line.lastIndexOf(":") + 1);
				System.out.println("FM: " + fm);
				if (fm.equals(mac)) {
					System.out.println("License Match!");
					return true;
				}
			}
			
			line = br.readLine();
		}
		
		if (!uidFound) {
		System.out.println("Registering...");
		fh.appendFile("data", uid + ":" + mac);
		return true;
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}	
}