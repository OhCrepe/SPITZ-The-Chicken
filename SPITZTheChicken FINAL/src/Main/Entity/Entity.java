package Main.Entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import Main.Animation.Animation;

public class Entity {

	protected int x, y, width, height, health, maxHealth, immuneCounter, immuneTime, xVel, yVel;
	protected boolean immunity, visible, activated;
	protected Animation animations[];
	protected ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>();
	
	public ArrayList<Rectangle> getCollisionRects() {
		return collisionRects;
	}

	public void setCollisionRects(ArrayList<Rectangle> collisionRects) {
		this.collisionRects = collisionRects;
	}

	public int getxVel() {
		return xVel;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public Entity(){
		
	}
	
	public Entity(int x, int y){
		this.x = x;
		this.y = y;
		this.immunity = false;
		this.immuneCounter = 0;
		this.visible = true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}

	public void doDamage(int damage) {
		if(!immunity){
			this.health -= damage;
			immuneCounter = immuneTime;
			immunity = true;
		}
	}
	
	public boolean isImmunity() {
		return immunity;
	}

	public void setImmunity(boolean immunity) {
		this.immunity = immunity;
	}

	public Animation getAnimation(int slot) {
		return animations[slot];
	}

	public void setAnimations(int slot, Animation animation) {
		this.animations[slot] = animation;
	}

	public void update(){

		for(int i = 0; i < animations.length; i++){
			animations[i].update();
		}
		if(immunity){
			immuneCounter -= 1;
			if(immuneCounter == 0){
				immunity = false;
			}
		}
		if(this.health > this.maxHealth){
			this.health = maxHealth;
		}
		if(this.visible){
			this.x = this.x + this.xVel;
			this.y = this.y + this.yVel;
		}
		fixCollisionRects();
		
	}
	
	public void fixCollisionRects(){
		
		this.collisionRects.clear();
		this.collisionRects.add(new Rectangle(this.x, this.y, this.width, this.height));
		
	}
	
}
