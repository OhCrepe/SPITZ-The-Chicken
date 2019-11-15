package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.gnet.packet.Packet;
import org.gnet.server.ClientModel;
import org.gnet.server.GNetServer;
import org.gnet.server.ServerEventListener;

public class Server {

	static String score = "0";
	
	public static void main(String[] args){
		
		try {
			FileReader file = new FileReader("resources/testscore.txt");
			BufferedReader reader = new BufferedReader(file);
			score = reader.readLine();
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String host = null;
		
		try {
			host = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		final int port = 43594;
		final GNetServer netserver = new GNetServer(host, port);
		System.out.println("Server starting");
		netserver.addEventListener(new ServerEventListener(){

			protected void clientConnected(ClientModel client) {
				
				System.out.println("Client connected to the Server: " + client.getUuid());
				Packet hiscore = new Packet("score", 1);
				hiscore.addEntry("hiscore", score);
				client.sendPacket(hiscore);
				return;
				
			}

			protected void clientDisconnected(ClientModel e) {

				System.out.println("Client disconnected from the server: " + e.getUuid());
				
			}

			protected void debugMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void errorMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void packetReceived(ClientModel client, Packet packet) {
				
				if(packet.getPacketName().equals("newscore")){
					String newscore = "" + packet.getEntry("newscore");
					System.out.println(newscore);
					if (Integer.parseInt(newscore) > Integer.parseInt(score)){
						score = newscore;
						File newFile = new File("resources/testscore.txt");
						if(!newFile.exists()){
							try {
								newFile.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							FileWriter writer = new FileWriter(newFile);
							BufferedWriter bWriter = new BufferedWriter(writer);
							bWriter.write("" + newscore);
							
							bWriter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
					return;
				}
				
			}
			
		});
		
		netserver.bind();
		netserver.start();
		netserver.enableServerMonitor();
		System.out.println("Server loaded");
		
	}
	
}
