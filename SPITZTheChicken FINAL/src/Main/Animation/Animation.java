package Main.Animation;

import java.awt.image.BufferedImage;

public class Animation {

	protected int frame, totalFrames, frameDelay, delayCounter, width, height;
	protected BufferedImage sprites[];
	
	public Animation(){
		
		this.sprites = new BufferedImage[totalFrames];
		
 	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void update(){
		
		if(delayCounter == 0){
			if(frame == totalFrames - 1){
				frame = 0;
			}else{
				frame += 1;
			}
			delayCounter = frameDelay;
		}else{
			delayCounter -= 1;
		}
		
	}
	
	public BufferedImage getSprite(){
		return sprites[frame];
	}

	public void setframeDelay(int i) {
		this.frameDelay = i;
		
	}
	
}
