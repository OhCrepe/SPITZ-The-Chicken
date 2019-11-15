package Main.Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CoinAnimation extends Animation{

	public CoinAnimation(){
		this.width = 16;
		this.height = 16;
		this.frame = 0;
		this.delayCounter = 0;
		this.frameDelay = 2;
		this.totalFrames = 10;
		this.sprites = new BufferedImage[totalFrames];
		try {
			for(int i = 0; i < totalFrames; i++){
				this.sprites[i] = ImageIO.read(new File("resources/sprites/Entities/Coins/Coin" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
