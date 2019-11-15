package Main.Entity;

import java.awt.Rectangle;
import java.io.IOException;

import Main.Animation.Animation;
import Main.Animation.SpitzBodyIdleRight;
import Main.Animation.SpitzHeadIdleRight;

public class Player extends Entity{

	int animHead = 0, animBody = 0;
	//0 = Idle       0 = Idle
	//               1 = Moving
	
	
	public Player(){
		
	}
	
	public int getAnimHead() {
		return animHead;
	}

	public void setAnimHead(int animHead) {
		this.animHead = animHead;
	}

	public int getAnimBody() {
		return animBody;
	}

	public void setAnimBody(int animBody) {
		this.animBody = animBody;
	}

	public Player(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 64;
		this.height = 64;
		this.health = 150;
		this.maxHealth = 100;
		this.immuneTime = 60;
		this.animations = new Animation[2];
		this.animations[0] = new SpitzHeadIdleRight();
		this.animations[1] = new SpitzBodyIdleRight();
			
	}
	
	public void update(){
		
		super.update();
		collisionRects.clear();
		Rectangle collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		collisionRects.add(collisionRect);
		
	}
	
	public void fixCollisionRects(){
		Rectangle collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		collisionRects.clear();
		collisionRects.add(collisionRect);
	}
	
}
