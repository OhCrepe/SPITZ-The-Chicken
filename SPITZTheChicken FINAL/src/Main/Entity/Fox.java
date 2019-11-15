package Main.Entity;

import java.io.IOException;

import Main.Animation.Animation;
import Main.Animation.FoxLeft;
import Main.Animation.FoxRight;

public class Fox extends Entity{
	
	int full = 60;
	
	public Fox(int x, int y){
		
		super(x, y);
		this.width = 96;
		this.height = 48;
		this.health = 50;
		this.maxHealth = 50;
		this.immuneTime = 5;
		this.xVel = 2;
		this.yVel = 0;
		this.activated = false;
		this.visible = false;
		this.animations = new Animation[1];
		try {
			this.animations[0] = new FoxRight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		super.update();
		if(full > 0){
			full -= 1;
		}
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

}
