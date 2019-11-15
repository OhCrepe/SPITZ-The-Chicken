package Main.Collectable;

import java.io.IOException;

import Main.Animation.LetterTAnimation;

public class LetterT extends Collectable{

	public LetterT(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new LetterTAnimation();
		
	}
	
}
