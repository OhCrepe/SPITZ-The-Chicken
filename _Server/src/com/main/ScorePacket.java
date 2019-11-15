package com.main;

import org.gnet.packet.Packet;

public class ScorePacket extends Packet{

	public ScorePacket(int score, String name) {
		
		super("scorePacket", 2);
		addEntry("player", name);
		addEntry("score", new Integer(score));
		
	}

}
