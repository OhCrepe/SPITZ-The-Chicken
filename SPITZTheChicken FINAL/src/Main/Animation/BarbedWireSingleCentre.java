package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarbedWireSingleCentre extends Animation{

	public BarbedWireSingleCentre() throws IOException{
		
		this.frameDelay = 60;
		this.totalFrames = 1;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 64;
		this.height = 64;
		this.sprites = new BufferedImage[totalFrames];
		this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/BarbedWire/BarbedSingleCentre.png"));
		
	}
	
}
