package Main.Tile;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DirtTile extends Tile{
	
	boolean left = false;
	boolean right = false;
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public DirtTile(int x, int y){
		
		super(x, y);
		this.solid = true;
		
		try {
			this.sprite = ImageIO.read(new File("resources/sprites/Tiles/DirtCentral.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
