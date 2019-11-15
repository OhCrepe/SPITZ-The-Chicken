package Main.Entities.Enemies;

import java.awt.Rectangle;

public class BarbedWire{
	
	private int x, y;
	private Rectangle collisionRect;
	private boolean visible = true;
	private int sprite;
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public BarbedWire(){
		
	}
	
	public BarbedWire(int x, int y){
		
		this.x = x;
		this.y = y;
		collisionRect = new Rectangle(x + 2, y + 2, 44, 44);
		
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

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}
	
	public int getSprite(){
		return sprite;
	}
	
	public void setSprite(int sprite){
		this.sprite = sprite;
	}
	
}
