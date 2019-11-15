package Main.Tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {

	protected int x, y, width, height;
	protected Rectangle collisionRect;
	protected boolean solid;
	protected boolean visible;
	protected boolean step = false;
	//Not step = 0
	//Left step = 1
	//Right step = 2
	public boolean isStep() {
		return step;
	}

	public void setStep(boolean step) {
		this.step = step;
	}

	BufferedImage sprite;
	
	public Tile(){
		
	}
	
	public Tile(int x, int y){
		
		this.x = x;
		this.y = y;
		this.width = 16;
		this.height = 16;
		this.collisionRect = new Rectangle(this.x, this.y, 16, 16);
		this.visible = false;
		this.sprite = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
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

	public int getHeight() {
		return height;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public BufferedImage getSprite() {
		return sprite;
	}
	
}
