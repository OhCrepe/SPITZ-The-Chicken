package Main.Obstacle;

import java.awt.Rectangle;

import Main.Animation.Animation;

public class Obstacle {

	protected int x, y, width, height, health, xVel, yVel;
	protected Rectangle collisionRect;
	protected Animation animation;
	protected boolean visible, destroyed;
	
	public int getxVel() {
		return xVel;
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

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public Obstacle(){
		
	}
	
	public Obstacle(int x, int y){
		
		this.x = x;
		this.y = y;
		this.xVel = 0;
		this.yVel = 0;
		this.visible = false;
		
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

	public void setHealth(int health) {
		this.health = health;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void update(){
		
		this.x += this.xVel;
		this.y += this.yVel;
		this.collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		if(this.health <= 0){
			this.destroyed = true;
		}
			
	}
	
	public void fixCollisionRect(){
		
		this.collisionRect = new Rectangle(this.x, this.y, this.width, this.height);
		
	}
	
	public void doDamage(int damage){
		this.health -= damage;
	}
	
}
