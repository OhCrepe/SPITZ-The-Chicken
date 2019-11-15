package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpitzHeadShootingLeft extends Animation{

	public SpitzHeadShootingLeft(){
		this.frameDelay = 6;
		this.totalFrames = 1;
		this.frame = 0;
		this.delayCounter = 6;
		this.width = 64;
		this.height = 68;
		this.sprites = new BufferedImage[totalFrames];
		try {
			this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/SPITZ/Head/Shooting/SPITZHeadLeftOpen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		super.update();
		if(this.delayCounter == this.frameDelay){
			try {
				this.sprites[0] = ImageIO.read(new File("resources/sprites/Entities/SPITZ/Head/Idle/SPITZHeadLeft.png"));
				this.frameDelay = 6000;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
