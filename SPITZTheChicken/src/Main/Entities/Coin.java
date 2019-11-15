package Main.Entities;

import java.awt.Rectangle;

public class Coin {

	int x, y;
	boolean collected, visible;
	Rectangle collisionRect;
	
	public Coin(){}
	
	public Coin(int x, int y){
		
		this.x = x;
		this.y = y;
		this.collected = false;
		this.visible = false;
		this.collisionRect = new Rectangle(this.x, this.y, 16, 16);
		
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
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

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
