package Main.Hazard;

import java.awt.Rectangle;

public class BarbedWire extends Hazard{
	
	public BarbedWire(int x, int y){
		
		super(x, y);
		this.width = 64;
		this.height = 64;
		this.collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		this.damage = 10;
		this.visible = false;
		
	}
	
}
