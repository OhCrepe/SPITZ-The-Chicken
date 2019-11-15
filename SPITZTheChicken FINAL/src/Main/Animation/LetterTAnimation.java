package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LetterTAnimation extends Animation{

	public LetterTAnimation() throws IOException{
		
		this.frameDelay = 30;
		this.totalFrames = 2;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 32;
		this.height = 32;
		this.sprites = new BufferedImage[totalFrames];
		this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/Letters/T1.png"));
		this.sprites[1] = ImageIO.read(new File("resources/sprites/Entities/Letters/T2.png"));
		
	}
	
}
