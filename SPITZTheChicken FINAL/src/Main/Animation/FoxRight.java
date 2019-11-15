package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FoxRight extends Animation{

public FoxRight() throws IOException{
		
		this.frameDelay = 8;
		this.totalFrames = 10;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 96;
		this.height = 50;
		this.sprites = new BufferedImage[totalFrames];
		this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight1.png"));
		this.sprites[1] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight2.png"));
		this.sprites[2] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight3.png"));
		this.sprites[3] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight3.png"));
		this.sprites[4] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight2.png"));
		this.sprites[5] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight1.png"));
		this.sprites[6] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight4.png"));
		this.sprites[7] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight5.png"));
		this.sprites[8] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight5.png"));
		this.sprites[9] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxRight4.png"));
		
	}
	
}
