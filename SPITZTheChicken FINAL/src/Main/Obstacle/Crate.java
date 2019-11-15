package Main.Obstacle;

import java.awt.Rectangle;

import Main.Animation.CrateHealthHigh;
import Main.Animation.CrateHealthLow;
import Main.Animation.CrateHealthMedium;

public class Crate extends Obstacle{

	public Crate(int x, int y){
		
		super(x, y);
		this.width = 64;
		this.height = 64;
		this.health = 25;
		this.yVel = 0;
		this.collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		this.animation = new CrateHealthHigh();
		
	}
	
	public void update(){
		
		if(this.visible){
			super.update();
			if(this.health <= 24){
				this.animation = new CrateHealthMedium();
				if(this.health <= 12){
					this.animation = new CrateHealthLow();
				}
			}
		}
	}
	
}
