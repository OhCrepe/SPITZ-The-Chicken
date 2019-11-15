package Main.Collectable;

import java.io.IOException;

import Main.Animation.LetterSAnimation;

public class LetterS extends Collectable{

	public LetterS(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new LetterSAnimation();
		
	}
	
}
