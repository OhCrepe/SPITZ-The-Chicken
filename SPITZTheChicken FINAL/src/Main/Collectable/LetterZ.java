package Main.Collectable;

import java.io.IOException;

import Main.Animation.LetterZAnimation;

public class LetterZ extends Collectable{

	public LetterZ(int x, int y) throws IOException{
		
		super(x, y);
		this.width = 32;
		this.height = 32;
		this.animation = new LetterZAnimation();
		
	}
	
}
