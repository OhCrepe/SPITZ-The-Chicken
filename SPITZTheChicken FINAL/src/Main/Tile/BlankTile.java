package Main.Tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlankTile extends Tile{
	
	public BlankTile(int x, int y){
		
		super(x, y);
		this.solid = false;
		
		try {
			this.sprite = ImageIO.read(new File("resources/sprites/Tiles/Sky.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
