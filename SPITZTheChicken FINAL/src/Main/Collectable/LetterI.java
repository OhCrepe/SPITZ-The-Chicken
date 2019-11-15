package Main.Collectable;

import java.io.IOException;

import Main.Animation.LetterIAnimation;

public class LetterI extends Collectable{

	public LetterI(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new LetterIAnimation();
		
	}
	
}
