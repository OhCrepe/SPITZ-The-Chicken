package Main.Entities.Enemies;

import java.awt.image.BufferedImage;

public class Enemy {

	private int x, y, width, height, frame, totalFrames;
	private BufferedImage sprites[];
	private boolean visible;
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public Enemy(){
		
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

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getTotalFrames() {
		return totalFrames;
	}

	public void setTotalFrames(int totalFrames) {
		this.totalFrames = totalFrames;
	}

	public BufferedImage getSprites() {
		return sprites[frame];
	}
	
	public void update(){
		if(frame < totalFrames){
			frame += 1;
		}else{
			frame = 0;
		}
	}
	
}
