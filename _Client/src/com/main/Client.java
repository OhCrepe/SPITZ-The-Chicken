package com.main;

import java.util.Random;

import org.gnet.client.ClientEventListener;
import org.gnet.client.GNetClient;
import org.gnet.client.ServerModel;
import org.gnet.packet.Packet;

public class Client {

	public static void main(String[] args){
		
		//host is what the client is connected too
		final String host = "25.107.106.196";
		final int port = 43594;
		final GNetClient netclient = new GNetClient(host, port);
		
		netclient.addEventListener(new ClientEventListener() {

			protected void clientConnected(ServerModel server) {

				System.out.println("Client connect to the server");
				
			}

			protected void clientDisconnected(ServerModel arg0) {
				
				System.out.println("Client Disconnected from Server");
				
			}

			protected void debugMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void errorMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}

			protected void packetReceived(ServerModel server, Packet packet) {
				
			}
			
		});
		
		netclient.bind();
		netclient.start();
		
	}
	
}
