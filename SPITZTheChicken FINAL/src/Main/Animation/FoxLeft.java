package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FoxLeft extends Animation{

public FoxLeft() throws IOException{
		
		this.frameDelay = 8;
		this.totalFrames = 10;
		this.frame = 0;
		this.delayCounter = 0;
		this.width = 96;
		this.height = 50;
		this.sprites = new BufferedImage[totalFrames];
		this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft1.png"));
		this.sprites[1] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft2.png"));
		this.sprites[2] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft3.png"));
		this.sprites[3] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft3.png"));
		this.sprites[4] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft2.png"));
		this.sprites[5] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft1.png"));
		this.sprites[6] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft4.png"));
		this.sprites[7] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft5.png"));
		this.sprites[8] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft5.png"));
		this.sprites[9] = ImageIO.read(new File("resources/sprites/Entities/Fox/FoxLeft4.png"));
		
	}
	
}
