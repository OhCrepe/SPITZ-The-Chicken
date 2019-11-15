package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SeedAnimation extends Animation{

	public SeedAnimation(){
		
		this.width = 6;
		this.height =6;
		this.frame = 0;
		this.delayCounter = 0;
		this.frameDelay = 60;
		this.totalFrames = 1;
		this.sprites = new BufferedImage[totalFrames];
		try {
			this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/Projectiles/Seed.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
