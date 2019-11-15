package Main.Projectile;

import java.awt.Rectangle;

import Main.Animation.Animation;

public class Projectile {

	protected int x, y, vel, damage, width, height;
	protected boolean gravity, removed;
	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	protected String direction;
	protected Rectangle collisionRect;
	protected Animation animation;
	
	public Projectile(){
		
	}
	
	public Projectile(int x, int y, String direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.collisionRect = new Rectangle(x, y, width, height);
		this.removed = false;
	}
	
	public void update(){
		
		//MOVEMENT
		if(direction.equals("UP")){
			y -= vel;
		} else if(direction.equals("DOWN")){
			y += vel;
			if(gravity){
				vel += 1;
				if(vel > 20){
					vel = 20;
				}
			}
		} else if(direction.equals("LEFT")){
			x -= vel;
		} else if(direction.equals("RIGHT")){
			x += vel;
		}
		
		collisionRect = new Rectangle(x, y, width, height);
		animation.update();
		
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

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
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

	public boolean isGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
}
