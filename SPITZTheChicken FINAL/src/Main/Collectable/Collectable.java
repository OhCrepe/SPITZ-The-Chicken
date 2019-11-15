package Main.Collectable;

import java.awt.Rectangle;

import Main.Animation.Animation;

public class Collectable {

	protected int x, y, width, height;
	protected Rectangle collisionRect;
	protected Animation animation;
	protected boolean visible, collected;
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
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
	
	public Collectable(){
		
	}
	
	public Collectable(int x, int y){
		this.x = x;
		this.y = y;
		collisionRect = new Rectangle(x - 2, y - 2, width + 4, height + 4);
		this.visible = false;
		this.collected = false;
	}

	public void update(){
		animation.update();
	}
	
}
