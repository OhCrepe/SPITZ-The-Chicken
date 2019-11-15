package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpitzBodyMovingLeft extends Animation{

	public SpitzBodyMovingLeft() throws IOException{
		
		this.frameDelay = 1;
		this.totalFrames = 18;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 64;
		this.height = 68;
		this.sprites = new BufferedImage[totalFrames];
		for(int i = 0; i < totalFrames; i++){
			this.sprites[i] = ImageIO.read(new File("resources/sprites/Entities/SPITZ/Body/Moving/SPITZMovingLeft" + (i + 1) + ".png"));
		}
		
	}
	
	public void update(){
		
		super.update();
		
	}
	
}
