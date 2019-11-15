package Main.Entity;

import java.awt.Rectangle;

import Main.Animation.Animation;
import Main.Animation.CannonPigIdle;

public class CannonPig extends Entity{

	protected int reloadTime, reloadCounter, attackDelay, attackCounter;
	
	public CannonPig(int x, int y){
		
		super(x, y);
		this.immuneTime = 4;
		this.reloadTime = 120;
		this.reloadCounter = 120;
		this.health = 100;
		this.maxHealth = 100;
		this.xVel = 0;
		this.yVel = 0;
		this.attackDelay = 60;
		this.attackCounter = 0;
		this.width = 80;
		this.height = 48;
		this.animations = new Animation[1];
		this.animations[0] = new CannonPigIdle();
		
	}
	
	public void update(){
		
		super.update();
		if(this.reloadCounter <= this.reloadTime){
			this.reloadCounter += 1;
		}
		this.fixCollisionRects();
		if(this.attackCounter > 0){
			this.attackCounter -= 1;
		}
		
	}
	
	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public int getReloadCounter() {
		return reloadCounter;
	}

	public void setReloadCounter(int reloadCounter) {
		this.reloadCounter = reloadCounter;
	}

	public int getAttackDelay() {
		return attackDelay;
	}

	public void setAttackDelay(int attackDelay) {
		this.attackDelay = attackDelay;
	}

	public int getAttackCounter() {
		return attackCounter;
	}

	public void setAttackCounter(int attackCounter) {
		this.attackCounter = attackCounter;
	}

	public void fixCollisionRects(){
		this.collisionRects.clear();
		Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
		collisionRects.add(rect);
	}
	
}
