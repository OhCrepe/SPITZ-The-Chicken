package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpitzBodyIdleRight extends Animation{

	public SpitzBodyIdleRight() throws IOException{
			
		this.frameDelay = 60;
		this.totalFrames = 1;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 64;
		this.height = 68;
		this.sprites = new BufferedImage[totalFrames];
		this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/SPITZ/Body/Idle/SPITZBodyRight.png"));
		
	}
	
}
