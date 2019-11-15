package Main.Entities;

import java.awt.Rectangle;

public class Crate{
	
	private int x, y, yVel;
	private int health;
	private Rectangle collisionRect;
	private boolean visible, activated;
	
	public Crate(){
		
	}
	
	public Crate(int x, int y){
		
		this.x = x;
		this.y = y;
		this.yVel = 0;
		this.health = 3;
		this.collisionRect = new Rectangle(x, y, 64, 64);
		this.setActivated(false);
		
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}

	public int getyVel() {
		return yVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
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

}
