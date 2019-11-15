package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HealthAnimation extends Animation{

	public HealthAnimation(){
		
		this.frameDelay = 2;
		this.totalFrames = 8;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 32;
		this.height = 32;
		this.sprites = new BufferedImage[totalFrames];
		try {
			for(int i = 0; i < totalFrames; i++){
				this.sprites[i] = ImageIO.read(new File("resources/sprites/Entities/Health/Health" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}
