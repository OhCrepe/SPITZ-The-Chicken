package Main.Goal;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Goal {

	protected int x, y, width, height;
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

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	protected BufferedImage sprite = new BufferedImage(80, 130, BufferedImage.TYPE_INT_ARGB);
	
	public Goal(int x, int y){
		
		this.x = x;
		this.y = y;
		this.width = 80;
		this.height = 128;
		try {
			this.sprite = ImageIO.read(new File("resources/sprites/Entities/Goal/Goal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}