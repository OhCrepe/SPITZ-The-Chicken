package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CannonPigIdle extends Animation{

	public CannonPigIdle(){
		
		this.frameDelay = 6;
		this.totalFrames = 6;
		this.frame = 0;
		this.delayCounter = 6;
		this.width = 80;
		this.height = 48;
		this.sprites = new BufferedImage[totalFrames];
		try {
			for(int i = 0; i < totalFrames; i++)
				this.sprites[i] = ImageIO.read(new File("resources/sprites/Entities/PigCannon/Idle/Sprite" + (i + 1) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
}
